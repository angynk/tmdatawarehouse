package com.datawarehouse.model.dao;

import com.datawarehouse.view.util.ConsultasOrdenDEF;
import com.datawarehouse.view.util.ExcelUtilProcessor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.engine.SessionImplementor;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Repository
@Transactional
public class KilometrosVaciosDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void generarReporteTotalVaciosOperador(String archivoReporte, Date fechaInicio, Date fechaFin) {

        String sqlQuery = "SELECT SUM(d.vacio_total*p.dias_aplica) as vacio, d.operador as operador " +
                "FROM dh_distribucion d " +
                "INNER JOIN dh_bus_registro b " +
                "ON d.bus_registro = b.id " +
                "INNER JOIN dh_cuadro c " +
                "ON b.cuadro = c.id " +
                "INNER JOIN dh_programacion p " +
                "ON c.programacion = p.id " +
                "WHERE p.fecha BETWEEN '"+fechaInicio+"' AND '"+fechaFin+"' "+
                "GROUP BY (d.operador)";

        HashMap<String,Integer> parametros = new HashMap<>();
        parametros.put(ConsultasOrdenDEF.KM_VACIOS,ConsultasOrdenDEF.KM_VACIOS_TOTAL_OPERADOR_VACIOS_COL);
        parametros.put(ConsultasOrdenDEF.OPERADOR,ConsultasOrdenDEF.KM_VACIOS_TOTAL_OPERADOR_OPERADOR_COL);

        String tituloReporte = "Vacios Por Operador";

        generarReporteKilometrosVacios(archivoReporte,sqlQuery,tituloReporte,parametros);

    }

    public void generarReporteKilometrosVacios(String archivoReporte, String sqlQuery, String tituloReporte, HashMap<String,Integer> parametros){
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        SessionImplementor sessImpl = (SessionImplementor) session;
        Connection conn = sessImpl.getJDBCContext().connection();

        Workbook wb = new HSSFWorkbook();
        Sheet personSheet = wb.createSheet(tituloReporte);

        try {
            PreparedStatement ps = conn.prepareStatement(sqlQuery);
            ResultSet query_set = ps.executeQuery();
            crearHeader(personSheet,parametros);
            int row = 1;
            while(query_set.next()) {
                crearContenido(personSheet,parametros,query_set,row);
                row = row + 1;
            }

            String outputDirPath = archivoReporte;
            FileOutputStream fileOut = new FileOutputStream(outputDirPath);
            wb.write(fileOut);
            fileOut.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void crearContenido(Sheet personSheet, HashMap<String, Integer> parametros, ResultSet query_set, int row) throws SQLException {
        Row rowInfo1 = personSheet.createRow(row);
        Iterator it = parametros.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            String value =(String)pair.getKey();
            if(value.equals(ConsultasOrdenDEF.KM_VACIOS)){
                ExcelUtilProcessor.createCellNumberResultados(rowInfo1,
                        query_set.getDouble((Integer)pair.getValue()+1),
                        (Integer)pair.getValue());
            }else{
                ExcelUtilProcessor.createCellResultados(rowInfo1,
                        query_set.getString((Integer)pair.getValue()+1),
                        (Integer)pair.getValue());
            }

        }
    }

    private void crearHeader(Sheet personSheet,HashMap<String, Integer> parametros) {
        Row rowInfo1 = personSheet.createRow(0);
        Iterator it = parametros.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            ExcelUtilProcessor.createCellResultados(rowInfo1, (String) pair.getKey() ,(Integer)pair.getValue());
        }

    }

    public void generarReporteDiaADiaVaciosOperador(String archivoReporte, Date fechaInicio, Date fechaFin) {
        String sqlQuery = "SELECT f.fecha ,SUM(d.vacio_total), d.operador " +
                    "FROM dh_distribucion d " +
                    "INNER JOIN dh_bus_registro b " +
                    "ON d.bus_registro = b.id " +
                    "INNER JOIN dh_cuadro c " +
                    "ON b.cuadro = c.id " +
                    "INNER JOIN dh_programacion p " +
                    "ON c.programacion = p.id " +
                    "INNER JOIN dh_fechas_programacion f " +
                    "ON p.id = f.programacion " +
                    "WHERE f.fecha BETWEEN '"+fechaInicio+"' AND '"+fechaFin+"'" +
                    "GROUP BY d.operador,f.fecha " +
                    "ORDER BY f.fecha";

        HashMap<String,Integer> parametros = new HashMap<>();
        parametros.put(ConsultasOrdenDEF.FECHA,ConsultasOrdenDEF.KM_VACIOS_OPERADOR_FECHA_COL);
        parametros.put(ConsultasOrdenDEF.KM_VACIOS,ConsultasOrdenDEF.KM_VACIOS_OPERADOR_VACIOS_COL);
        parametros.put(ConsultasOrdenDEF.OPERADOR,ConsultasOrdenDEF.KM_VACIOS_OPERADOR_OPERADOR_COL);

        String tituloReporte = "Vacios Por Operador";

        generarReporteKilometrosVacios(archivoReporte,sqlQuery,tituloReporte,parametros);

    }


    public void generarReporteDiaADiaVaciosServicio(String archivoReporte, Date fechaInicio, Date fechaFin) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        SessionImplementor sessImpl = (SessionImplementor) session;
        Connection conn = null;
        conn = sessImpl.getJDBCContext().connection();
        CopyManager copyManager = null;
        BufferedWriter fileWriter = null;
        try {
            fileWriter = new BufferedWriter(new FileWriter(archivoReporte));
            String sqlQuery = "SELECT f.fecha ,SUM(d.kilometros), d.linea, d.punto_inicio " +
                    "FROM dh_expediciones d " +
                    "INNER JOIN dh_bus_registro b " +
                    "ON d.bus_registro = b.id " +
                    "INNER JOIN dh_cuadro c " +
                    "ON b.cuadro = c.id " +
                    "INNER JOIN dh_programacion p " +
                    "ON c.programacion = p.id " +
                    "INNER JOIN dh_fechas_programacion f " +
                    "ON p.id = f.programacion " +
                    "WHERE (d.tipo = 'VEx' OR d.tipo = 'VPa' OR d.tipo = 'VIn') AND  f.fecha BETWEEN '"+fechaInicio+"' AND '"+fechaFin+"'" +
                    "GROUP BY d.linea,f.fecha , d.punto_inicio " +
                    "ORDER BY f.fecha, d.linea";


            copyManager = new CopyManager((BaseConnection) conn);
//            copyManager.copyOut("COPY ("+sqlQuery+") TO STDOUT WITH DELIMITER ';'",fileWriter);
            ((BaseConnection) conn).execSQLQuery("COPY ("+sqlQuery+") TO 'C:\\\\temp\\resultado.csv'"+" WITH DELIMITER ';'");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
