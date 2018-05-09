package com.datawarehouse.model.dao;

import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.view.util.ConsultaBasicaDEF;
import com.datawarehouse.view.util.ConsultasOrdenDEF;
import com.datawarehouse.view.util.ExcelUtilProcessor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.engine.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Repository
@Transactional
public class ExportarDatosBaseDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void generarReporte(String archivoReporte, String sqlQuery, String tituloReporte, HashMap<String,Integer> parametros){
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
            if(value.equals(ConsultaBasicaDEF.EXP_TX_KM )|| value.equals(ConsultaBasicaDEF.EXP_TX_DE) || value.equals(ConsultaBasicaDEF.EXP_TX_A)){
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

    public void generarReporteExpedicionesPorCuadro(Cuadro cuadroObj, String archivoReporte) {
        String sqlQuery = "Select e.evento,e.tipo,e.inicio,e.punto_inicio, e.fin, e.punto_fin, e.duracion, \n" +
                "r.bus, e.linea, e.kilometros, e.inferido , e.identificador, e.frec, e.serbus, e.des_dur, e.des_frec \n" +
                "from dh_expediciones e \n" +
                "INNER JOIN dh_bus_registro r ON e.bus_registro = r.id \n" +
                "WHERE r.cuadro ="+cuadroObj.getId();

        HashMap<String,Integer> parametros = new HashMap<>();
        parametros.put(ConsultaBasicaDEF.EXP_TX_EVENTO,ConsultaBasicaDEF.EXP_EVENTO);
        parametros.put(ConsultaBasicaDEF.EXP_TX_TIPO,ConsultaBasicaDEF.EXP_TIPO);
        parametros.put(ConsultaBasicaDEF.EXP_TX_INICIO,ConsultaBasicaDEF.EXP_INICIO);
        parametros.put(ConsultaBasicaDEF.EXP_TX_DE,ConsultaBasicaDEF.EXP_DE);
        parametros.put(ConsultaBasicaDEF.EXP_TX_FIN,ConsultaBasicaDEF.EXP_FIN);
        parametros.put(ConsultaBasicaDEF.EXP_TX_A,ConsultaBasicaDEF.EXP_A);
        parametros.put(ConsultaBasicaDEF.EXP_TX_DUR,ConsultaBasicaDEF.EXP_DUR);
        parametros.put(ConsultaBasicaDEF.EXP_TX_BUS,ConsultaBasicaDEF.EXP_BUS);
        parametros.put(ConsultaBasicaDEF.EXP_TX_LINEA,ConsultaBasicaDEF.EXP_LINEA);
        parametros.put(ConsultaBasicaDEF.EXP_TX_KM,ConsultaBasicaDEF.EXP_KM);
        parametros.put(ConsultaBasicaDEF.EXP_TX_INFERIDO,ConsultaBasicaDEF.EXP_INFERIDO);
        parametros.put(ConsultaBasicaDEF.EXP_TX_ID,ConsultaBasicaDEF.EXP_ID);
        parametros.put(ConsultaBasicaDEF.EXP_TX_FREC,ConsultaBasicaDEF.EXP_FREC);
        parametros.put(ConsultaBasicaDEF.EXP_TX_SERBUS,ConsultaBasicaDEF.EXP_SERBUS);
        parametros.put(ConsultaBasicaDEF.EXP_TX_DESA,ConsultaBasicaDEF.EXP_DESA);
        parametros.put(ConsultaBasicaDEF.EXP_TX_DESB,ConsultaBasicaDEF.EXP_DESB);

        String tituloReporte = "Vacios Por Operador";

        generarReporte(archivoReporte,sqlQuery,tituloReporte,parametros);

    }

}
