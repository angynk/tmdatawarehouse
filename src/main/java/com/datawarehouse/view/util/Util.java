package com.datawarehouse.view.util;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;


public class Util {

    public static HttpSession getSession(){
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest(){
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static List<String> listaDePeriocidad(){
        List<String> periocidad = new ArrayList<>();
        periocidad.add("HABIL");
        periocidad.add("SABADO");
        periocidad.add("FESTIVO");
        return periocidad;
    }

    public static List<String> listaDeTipologia(){
        List<String> tipologia = new ArrayList<>();
        tipologia.add("ART");
        tipologia.add("BI");
        tipologia.add("DEF");
        return tipologia;
    }

    public static List<String> listaDeTipoGrafica(){
        List<String> tipoGrafica = new ArrayList<>();
        tipoGrafica.add("Con Saltos");
        tipoGrafica.add("Con Repeticiones");
        return tipoGrafica;
    }

    public static List<String> listaTipoArchivos() {
        List<String> tipoArchivos = new ArrayList<>();
//        tipoArchivos.add(TipoArchivo.expediciones);
        tipoArchivos.add(TipoArchivo.buses);
        tipoArchivos.add(TipoArchivo.distribuciones);
        tipoArchivos.add(TipoArchivo.tracelog);
        tipoArchivos.add(TipoArchivo.tablaHorario);
        tipoArchivos.add(TipoArchivo.matrizDistancia);
        return tipoArchivos;
    }

    public static List<String> listaFormatosArchivo() {
        List<String> formatoArchivos = new ArrayList<>();
        formatoArchivos.add(FormatoArchivo.CSV_COMMA);
        formatoArchivos.add(FormatoArchivo.CSV_PUNTO_COMMA);
        formatoArchivos.add(FormatoArchivo.TXT);
        formatoArchivos.add(FormatoArchivo.XLS);
        return formatoArchivos;
    }

    public static Integer convertirANumero(String numBus) {
        try{
            return Integer.parseInt(numBus);
        }catch (Exception e){
            return 0;
        }

    }

    public static Time convertirTime(String tiempo) {
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        try {
            Time time = new Time(sdf.parse(tiempo).getTime());
            return time;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Time(new Date().getTime());
    }

    public static Integer formatoPuntos(String punto) {
        Integer puntoFormato = Integer.parseInt(punto);
        if(puntoFormato>5000000){
            return puntoFormato - 5000000;
        }
        return puntoFormato - 1000000;
    }

    public static Double formatoKilometros(String km) {
        Double kilometros = Double.parseDouble(km);
        return kilometros;
    }

    public static List<String> listaFormatosCSV() {

        List<String> formatoArchivos = new ArrayList<>();
        formatoArchivos.add(FormatoArchivo.CSV_COMMA);
        formatoArchivos.add(FormatoArchivo.CSV_PUNTO_COMMA);
        return formatoArchivos;
    }

    public static List<String> listaModos() {
        List<String> modos = new ArrayList<>();
        modos.add(Modos.TRONCAL);
        modos.add(Modos.DUAL);
//        modos.add(Modos.ALIMENTADOR);
        return modos;
    }

    public static String convertirModo(String modo) {
        if (Modos.TRONCAL.equals(modo)){
            return "T";
        }else if (Modos.ALIMENTADOR.equals(modo)){
            return "A";
        }
        return "D";
    }

    public static String md5(String data) {
        // Get the algorithm:
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // Calculate Message Digest as bytes:
        byte[] digest = md5.digest(data.getBytes(UTF_8));
        // Convert to 32-char long String:
        return DatatypeConverter.printHexBinary(digest);
    }

    public static List<String> listaConsultasBasicas() {
        List<String> tipoArchivos = new ArrayList<>();
        tipoArchivos.add(TipoArchivo.expediciones);
//        tipoArchivos.add(TipoArchivo.buses);
//        tipoArchivos.add(TipoArchivo.distribuciones);
//        tipoArchivos.add(TipoArchivo.tracelog);
//        tipoArchivos.add(TipoArchivo.tablaHorario);
//        tipoArchivos.add(TipoArchivo.matrizDistancia);
        return tipoArchivos;
    }

    public static void descargarArchivo(String path,String nombreFile) throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        File file = new File(path);
        file.createNewFile();
        FileInputStream fileIn = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename="+nombreFile);


        byte[] outputByte = new byte[4096];
        while(fileIn.read(outputByte, 0, 4096) != -1)
        {
            out.write(outputByte, 0, 4096);
        }
        fileIn.close();
        out.flush();
        out.close();
        out.flush();
        try {
            if (out != null) {
                out.close();
            }
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {

        }
    }

    public static List<String> listaConsultasAvanzadas() {
        List<String> consultas = new ArrayList<>();
        consultas.add(Consulta.KM_VACIOS_OPERADOR);
        consultas.add(Consulta.KM_VACIOS_SERVICIO);
        return consultas;
    }

    public static List<String> listaTiposConsulta() {
        List<String> tiposConsulta = new ArrayList<>();
        tiposConsulta.add(TipoConsulta.TOTALES);
        tiposConsulta.add(TipoConsulta.POR_DIA);
        return tiposConsulta;
    }

    public static String convertirAString(Date fecha) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        return sdfDate.format(fecha);
    }
}
