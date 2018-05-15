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

        String tituloReporte = "Expediciones Cuadro";

        generarReporte(archivoReporte,sqlQuery,tituloReporte,parametros);

    }

    public void generarReporteBusesPorCuadro(Cuadro cuadroObj, String archivoReporte) {
        String sqlQuery = "Select r.bus, e.inicio, e.fin, e.jornada, e.circ ,e.punto_inicio, \n" +
                "e.punto_fin, e.linea_inicio, e.linea_fin, e.kilometros, e.tipologia, e.bus_bd, e.operador \n" +
                "from dh_buses e \n" +
                "INNER JOIN dh_bus_registro r ON e.bus_registro = r.id \n" +
                "WHERE r.cuadro ="+cuadroObj.getId();

        HashMap<String,Integer> parametros = new HashMap<>();
        parametros.put(ConsultaBasicaDEF.BUS_TX_BUS,ConsultaBasicaDEF.BUS_BUS);
        parametros.put(ConsultaBasicaDEF.BUS_TX_INICIO,ConsultaBasicaDEF.BUS_INICIO);
        parametros.put(ConsultaBasicaDEF.BUS_TX_FIN,ConsultaBasicaDEF.BUS_FIN);
        parametros.put(ConsultaBasicaDEF.BUS_TX_JOR,ConsultaBasicaDEF.BUS_JOR);
        parametros.put(ConsultaBasicaDEF.BUS_TX_CIR,ConsultaBasicaDEF.BUS_CIR);
        parametros.put(ConsultaBasicaDEF.BUS_TX_DE,ConsultaBasicaDEF.BUS_DE);
        parametros.put(ConsultaBasicaDEF.BUS_TX_A,ConsultaBasicaDEF.BUS_A);
        parametros.put(ConsultaBasicaDEF.BUS_TX_LDE,ConsultaBasicaDEF.BUS_LDE);
        parametros.put(ConsultaBasicaDEF.BUS_TX_LA,ConsultaBasicaDEF.BUS_LA);
        parametros.put(ConsultaBasicaDEF.BUS_TX_KM,ConsultaBasicaDEF.BUS_KM);
        parametros.put(ConsultaBasicaDEF.BUS_TX_TIPO,ConsultaBasicaDEF.BUS_TIPO);
        parametros.put(ConsultaBasicaDEF.BUS_TX_BUSBD,ConsultaBasicaDEF.BUS_BUSBD);
        parametros.put(ConsultaBasicaDEF.BUS_TX_OPERADOR,ConsultaBasicaDEF.BUS_OPERADOR);

        String tituloReporte = "Buses Cuadro";

        generarReporte(archivoReporte,sqlQuery,tituloReporte,parametros);
    }

    public void generarReporteDistribucionesPorCuadro(Cuadro cuadroObj, String archivoReporte) {

        String sqlQuery = "Select r.bus, e.operador,e.patio_ini, e.patio_fin, e.patio_valle, e.tipologia," +
                "e.punto_inicio, e.punto_fin, e.distancia, e.vacio_interno_sin_valle,e.km_vacio_valle, e.vacio_externo," +
                " e.vacio_total, e.punto_inicio_valle, e.punto_fin_valle, e.duracion_valle, e.reserva  \n" +
                "from dh_distribucion e \n" +
                "INNER JOIN dh_bus_registro r ON e.bus_registro = r.id \n" +
                "WHERE r.cuadro ="+cuadroObj.getId();

        HashMap<String,Integer> parametros = new HashMap<>();
        parametros.put(ConsultaBasicaDEF.DIS_TX_SERBUS,ConsultaBasicaDEF.DIS_SERBUS);
        parametros.put(ConsultaBasicaDEF.DIS_TX_OPERADOR,ConsultaBasicaDEF.DIS_OPERADOR);
        parametros.put(ConsultaBasicaDEF.DIS_TX_PATIO_INICIO,ConsultaBasicaDEF.DIS_PATIO_INICIO);
        parametros.put(ConsultaBasicaDEF.DIS_TX_PATIO_FIN,ConsultaBasicaDEF.DIS_PATIO_FIN);
        parametros.put(ConsultaBasicaDEF.DIS_TX_PATIO_VALLE,ConsultaBasicaDEF.DIS_PATIO_VALLE);
        parametros.put(ConsultaBasicaDEF.DIS_TX_TIPOLOGIA,ConsultaBasicaDEF.DIS_TIPOLOGIA);
        parametros.put(ConsultaBasicaDEF.DIS_TX_PUNTO_INICIO,ConsultaBasicaDEF.DIS_PUNTO_INICIO);
        parametros.put(ConsultaBasicaDEF.DIS_TX_PUNTO_FIN,ConsultaBasicaDEF.DIS_PUNTO_FIN);
        parametros.put(ConsultaBasicaDEF.DIS_TX_DISTANCIA,ConsultaBasicaDEF.DIS_DISTANCIA);
        parametros.put(ConsultaBasicaDEF.DIS_TX_VACIO_INTERNO,ConsultaBasicaDEF.DIS_VACIO_INTERNO);
        parametros.put(ConsultaBasicaDEF.DIS_TX_KM_VACIO_VALLE,ConsultaBasicaDEF.DIS_KM_VACIO_VALLE);
        parametros.put(ConsultaBasicaDEF.DIS_TX_VACIO_EXTERNO,ConsultaBasicaDEF.DIS_VACIO_EXTERNO);
        parametros.put(ConsultaBasicaDEF.DIS_TX_VACIO_TOTAL,ConsultaBasicaDEF.DIS_VACIO_TOTAL);
        parametros.put(ConsultaBasicaDEF.DIS_TX_PUNTO_INICIO_VALLE,ConsultaBasicaDEF.DIS_PUNTO_INICIO_VALLE);
        parametros.put(ConsultaBasicaDEF.DIS_TX_PUNTO_FIN_VALLE,ConsultaBasicaDEF.DIS_PUNTO_FIN_VALLE);
        parametros.put(ConsultaBasicaDEF.DIS_TX_DUR_VALLE,ConsultaBasicaDEF.DIS_DUR_VALLE);
        parametros.put(ConsultaBasicaDEF.DIS_TX_RESERVA,ConsultaBasicaDEF.DIS_RESERVA);

        String tituloReporte = "Distribución Cuadro";

        generarReporte(archivoReporte,sqlQuery,tituloReporte,parametros);

    }

    public void generarReporteTablaHorarioPorCuadro(Cuadro cuadroObj, String archivoReporte) {
        String sqlQuery = "Select e.jornada_tipo, e.tipo_dia, e.operador,e.instante, r.bus, e.evento, e.linea, " +
                "e.coche, e.sublinea, e.ruta, e.punto, e.tipo_nodo, e.viaje  \n" +
                "from dh_tabla_horario e \n" +
                "INNER JOIN dh_bus_registro r ON e.bus_registro = r.id \n" +
                "WHERE r.cuadro ="+cuadroObj.getId();

        HashMap<String,Integer> parametros = new HashMap<>();
        parametros.put(ConsultaBasicaDEF.IPH_TX_JORNADA_TIPO,ConsultaBasicaDEF.IPH_JORNADA_TIPO);
        parametros.put(ConsultaBasicaDEF.IPH_TX_TIPO_DIA,ConsultaBasicaDEF.IPH_TIPO_DIA);
        parametros.put(ConsultaBasicaDEF.IPH_TX_OPERADOR,ConsultaBasicaDEF.IPH_OPERADOR);
        parametros.put(ConsultaBasicaDEF.IPH_TX_INSTANTE,ConsultaBasicaDEF.IPH_INSTANTE);
        parametros.put(ConsultaBasicaDEF.IPH_TX_SERBUS,ConsultaBasicaDEF.IPH_SERBUS);
        parametros.put(ConsultaBasicaDEF.IPH_TX_EVENTO,ConsultaBasicaDEF.IPH_EVENTO);
        parametros.put(ConsultaBasicaDEF.IPH_TX_LINEA,ConsultaBasicaDEF.IPH_LINEA);
        parametros.put(ConsultaBasicaDEF.IPH_TX_COCHE,ConsultaBasicaDEF.IPH_COCHE);
        parametros.put(ConsultaBasicaDEF.IPH_TX_SUBLINEA,ConsultaBasicaDEF.IPH_SUBLINEA);
        parametros.put(ConsultaBasicaDEF.IPH_TX_RUTA,ConsultaBasicaDEF.IPH_RUTA);
        parametros.put(ConsultaBasicaDEF.IPH_TX_PUNTO,ConsultaBasicaDEF.IPH_PUNTO);
        parametros.put(ConsultaBasicaDEF.IPH_TX_TIPO_NODO,ConsultaBasicaDEF.IPH_TIPO_NODO);
        parametros.put(ConsultaBasicaDEF.IPH_TX_VIAJE,ConsultaBasicaDEF.IPH_VIAJE);

        String tituloReporte = "IPH Cuadro";

        generarReporte(archivoReporte,sqlQuery,tituloReporte,parametros);
    }
}
