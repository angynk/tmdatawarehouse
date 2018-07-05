package com.datawarehouse.model.servicios;

import com.datawarehouse.model.dao.*;
import com.datawarehouse.model.entity.Archivos;
import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.FechasProg;
import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.view.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service("CargaDatosServicios")
public class CargaDatosServicios {

    @Autowired
    private ProgramacionDao programacionDao;

    @Autowired
    private ArchivosDao archivosDao;

    @Autowired
    private CuadroDao cuadroDao;

    @Autowired
    private FechasProgDao fechasProgDao;

    @Autowired
    private ExportarDatosBaseDao exportarDatosBaseDao;


    @Autowired
    private ProcessorUtils processorUtils;

    @Autowired
    private CargaExpedicionesServicio cargaExpedicionesServicio;

    @Autowired
    private CargaBusesServicio cargaBusesServicio;

    @Autowired
    private CargaTablaHorarioServicio cargaTablaHorarioServicio;

    @Autowired
    private CargaTracelogServicio cargaTracelogServicio;

    @Autowired
    private CargaDistribucionesServicio cargaDistribucionesServicio;

    @Autowired
    private ExportarExpedicionesProcessor exportarExpedicionesProcessor;


    public boolean existeProgramacion(String identificador) {
        Programacion programacion = programacionDao.encontrarProgramacion(identificador);
        if(programacion!=null) return true;
        return false;
    }

    public void agregarProgramacion(Programacion programacion) {
        programacionDao.addProgramacion(programacion);
    }

    public String copyFile(String fileName, InputStream in){
        processorUtils.copyFile(fileName,in, PathFiles.PATH);
        return fileName;
    }

    public List<LogDatos> cargarArchivoNuevo(Archivos archivo, List<LogDatos> logDatos, Programacion programacion,Cuadro cuadro) throws Exception {
        if(archivo.getGrupo().equals(TipoArchivo.expediciones)){

        }else if(archivo.getGrupo().equals(TipoArchivo.buses)){
              logDatos = cargaBusesServicio.agregarInformacionBuses(programacion,archivo,logDatos,cuadro);
        }else if(archivo.getGrupo().equals(TipoArchivo.tracelog)){
              logDatos = cargaTracelogServicio.agregarDatosTracelog(programacion,archivo,logDatos);
        }else if(archivo.getGrupo().equals(TipoArchivo.distribuciones)){
              logDatos = cargaDistribucionesServicio.agregarDatosDistribuciones(programacion,archivo,logDatos);
        }else if(archivo.getGrupo().equals(TipoArchivo.tablaHorario)){
               logDatos = cargaTablaHorarioServicio.agregarInformacionTablaHorario(programacion,archivo,logDatos,cuadro);
        }else if(archivo.getGrupo().equals(TipoArchivo.matrizDistancia)){

        }
        return logDatos;
    }



    public String incluirFilaArchivo(String nombre, Programacion nuevaProgramacion, String tipo,String modo,String numeroCuadro) throws IOException {
        if(tipo.equals(FormatoArchivo.CSV_COMMA)){
            return cargaExpedicionesServicio.incluirFilaArchivo(nombre,nuevaProgramacion,modo,numeroCuadro);
        }
        return cargaExpedicionesServicio.incluirFilaArchivoPuntoComa(nombre,nuevaProgramacion,modo,numeroCuadro);

    }

    public void cargarArchivoExpediciones(String nombre) throws Exception {
        cargaExpedicionesServicio.cargarArchivoExpediciones(nombre);
    }

    public void eliminarDatosTemporales(Programacion nuevaProgramacion) {
        cargaExpedicionesServicio.eliminarDatosTemporales(nuevaProgramacion);
    }

    public List<Programacion> getProgramaciones(Date fechaInicio, Date fechaFin, String tipoDia,String modo) {
        return programacionDao.getProgramaciones(fechaInicio,fechaFin,tipoDia,modo);
    }

    public void agregarArchivo(Archivos archivo) {
        archivosDao.addArchivos(archivo);
    }

    public List<Archivos> obtenerArchivosLista(Cuadro cuadro) {
        return archivosDao.encontrarArchivos(cuadro);
    }

    public Programacion obtenerProgramacion(String progr) {
        return programacionDao.encontrarProgramacion(progr);
    }

    public List<Cuadro> obtenerCuadrosProgramacion(Programacion progr) {
        return cuadroDao.obtenerCuadrosProgramacion(progr);
    }

    public void guardarCuadro(Cuadro nuevoCuadro) {
        cuadroDao.addCuadro(nuevoCuadro);
    }

    public Cuadro obtenerCuadro(Programacion programacion, String cuadro) {

        return cuadroDao.obtenerCuadro(programacion,cuadro);
    }

    public void generarConsultaExpediciones(String consultaPath, Programacion programacion, String cuadro) {
        Cuadro cuadroObj = obtenerCuadro(programacion,cuadro);
        exportarDatosBaseDao.generarReporteExpedicionesPorCuadro(cuadroObj,consultaPath);
    }


    public boolean agregarProgramacionAFechas(List<Date> fechasRecords, Programacion nuevaProgramacion) {
        try{
            for(Date fecha:fechasRecords){
                FechasProg fechasProg = new FechasProg();
                fechasProg.setFecha(fecha);
                fechasProg.setModo(nuevaProgramacion.getModo());
                fechasProg.setProgramacion(nuevaProgramacion);
                fechasProgDao.addFechaProg(fechasProg);
            }
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return  false;
    }

    public boolean existeProgramacionFechas(List<Date> fechasRecords) {
        for(Date fecha:fechasRecords){
            if(fechasProgDao.existeFecha(fecha) !=null){
                return true;
            }
        }
        return false;
    }

    public List<Programacion> getProgramaciones(Date fechaInicio, Date fechaFin, String modo) {
        return programacionDao.getProgramaciones(fechaInicio,fechaFin,modo);
    }

    public void agregarInformacionBuses(Programacion programacionSelected, Archivos archivo, List<LogDatos> logDatos, Cuadro nuevoCuadro) throws Exception {
        cargaBusesServicio.agregarInformacionBuses(programacionSelected,archivo,logDatos,nuevoCuadro);
    }

    public void eliminarDatosArchivo(Archivos selectedArchivo) {
        if(selectedArchivo.getGrupo().equals(TipoArchivo.expediciones)){
            cargaExpedicionesServicio.eliminarDatosDeCuadro(selectedArchivo.getCuadro());
        }else if (selectedArchivo.getGrupo().equals(TipoArchivo.buses)){
            cargaBusesServicio.eliminarDatosDeCuadro(selectedArchivo.getCuadro());
        }else if (selectedArchivo.getGrupo().equals(TipoArchivo.tablaHorario)){
            cargaTablaHorarioServicio.eliminarDatosDeCuadro(selectedArchivo.getCuadro());
        }else if (selectedArchivo.getGrupo().equals(TipoArchivo.distribuciones)){
            cargaDistribucionesServicio.eliminarDatosDeCuadro(selectedArchivo.getCuadro());
        }
        archivosDao.deleteArchivo(selectedArchivo);
    }

    public void generarConsultaBuses(String consultaPath, Programacion programacion, String cuadro) {
        Cuadro cuadroObj = obtenerCuadro(programacion,cuadro);
        exportarDatosBaseDao.generarReporteBusesPorCuadro(cuadroObj,consultaPath);

    }

    public void generarConsultaDistribuciones(String consultaPath, Programacion programacion, String cuadro) {
        Cuadro cuadroObj = obtenerCuadro(programacion,cuadro);
        exportarDatosBaseDao.generarReporteDistribucionesPorCuadro(cuadroObj,consultaPath);
    }

    public void generarConsultaTablaHorario(String consultaPath, Programacion programacion, String cuadro) {
        Cuadro cuadroObj = obtenerCuadro(programacion,cuadro);
        exportarDatosBaseDao.generarReporteTablaHorarioPorCuadro(cuadroObj,consultaPath);
    }

    public boolean noExisteElNumeroDeCuadro(String numero) {
        List<Cuadro> cuadros =cuadroDao.obtenerCuadroNumero(numero);
        if(cuadros.size()>0) return false;
        return true;
    }

    public boolean existeProgramacionEnFecha(Date fecha, String modo) {
        List<Programacion> programaciones = programacionDao.getProgramacionFecha(fecha,modo);
        if(programaciones.size()>0)return true;
        return false;
    }
}
