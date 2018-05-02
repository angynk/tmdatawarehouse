package com.datawarehouse.model.servicios;

import com.datawarehouse.model.dao.*;
import com.datawarehouse.model.entity.*;
import com.datawarehouse.view.util.ProcessorUtils;
import com.datawarehouse.view.util.TipoProgramacion;
import com.datawarehouse.view.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("DuplicarProcessor")
public class DuplicarProgramacionProcessor {

    private List<String> logDatos;

    @Autowired
    private ProgramacionDao programacionDao;

    @Autowired
    private FechasProgDao fechasProgDao;

    @Autowired
    private CuadroDao cuadroDao;

    @Autowired
    private ArchivosDao archivosDao;

    @Autowired
    private BusRegistroDao busRegistroDao;


    public DuplicarProgramacionProcessor() {
    }



    public boolean existeProgramacionParaLaFecha(String fechas, String modo){
        List<Date> fechasRecords = ProcessorUtils.convertirAfechas(fechas);
        if(fechasRecords.size()>0){
            Programacion programacionbyFecha = programacionDao.getProgramacionbyFechaModo(fechasRecords.get(0),modo);
            if(programacionbyFecha!=null){
                return true;
            }else{
                List<FechasProg>  fechaAsociadas = fechasProgDao.getfechasByModo(fechasRecords.get(0),modo);
                if(fechaAsociadas.size()>0){
                    return true;
                }
            }
        }
        return false;
    }


    public List<String> duplicarProgramacion(Date fechaADuplicar, String fechas, String modo, String razonProgramacion) {
        logDatos = new ArrayList<>();

        List<Date> fechasRecords = ProcessorUtils.convertirAfechas(fechas);
        Programacion programacionbyFecha = programacionDao.getProgramacionbyFechaModo(fechaADuplicar, Util.convertirModo(modo));

        //  Duplicar Programacion
        Programacion nuevaProgramacion = copiarDatosProgramaicon(programacionbyFecha,razonProgramacion,fechasRecords);
        programacionDao.addProgramacion(nuevaProgramacion);

        //Asociar Programacion a Dias que aplica
        agregarProgramacionAFechas(fechasRecords,nuevaProgramacion);

        //Duplicar Cuadros y archivos
        List<Cuadro> cuadrosProgramacion = cuadroDao.obtenerCuadrosProgramacion(programacionbyFecha);
        for(Cuadro cuadro:cuadrosProgramacion){
                duplicarDatosCuadro(cuadro,nuevaProgramacion);
        }



        return null;
    }

    private void duplicarDatosCuadro(Cuadro cuadro, Programacion nuevaProgramacion) {
        java.sql.Date dateSql = new java.sql.Date(cuadro.getFecha().getTime());

        //Duplicar Cuadro
        Cuadro cuadroNuevo = copiarDatosCuadro(cuadro,nuevaProgramacion);
        cuadroDao.addCuadro(cuadroNuevo);

        //Duplicar Archivos Lista
        List<Archivos> archivosCuadroViejo = archivosDao.encontrarArchivos(cuadro);
        List<Archivos> archivosNuevo = copiarArchivos(archivosCuadroViejo,cuadroNuevo);


        llamarFunctionPSQLDuplicacion(cuadro,cuadroNuevo,dateSql,false);

    }

    private String llamarFunctionPSQLDuplicacion(Cuadro cuadroViejo, Cuadro cuadroNuevo, java.sql.Date fecha, Boolean duplicarDistribuciones) {
        return cuadroDao.duplicarDatosCuadro(cuadroViejo,cuadroNuevo,fecha,duplicarDistribuciones);
    }

    private List<BusRegistro> duplicarBusesRegistro(List<BusRegistro> busesRegistroViejo, Cuadro cuadroNuevo) {

        for(BusRegistro busRegistroViejo : busesRegistroViejo){
            //Crear nuevo bus
            BusRegistro busRegistroNuevo = crearNuevoBusRegistro(busRegistroViejo,cuadroNuevo);
            //Bus en expediciones

            //Bus en buses

            //Bus en IPH

            //Bus en Distribuciones

        }

        return null;
    }

    private BusRegistro crearNuevoBusRegistro(BusRegistro busesRegistroViejo, Cuadro cuadroNuevo) {
        BusRegistro nuevo = new BusRegistro();
        nuevo.setOperador(busesRegistroViejo.getOperador());
        nuevo.setBus(busesRegistroViejo.getBus());
        nuevo.setCuadro(cuadroNuevo);
        nuevo.setFecha(cuadroNuevo.getFecha());
        busRegistroDao.addBusRegistro(nuevo);
        return nuevo;
    }

    private List<Archivos> copiarArchivos(List<Archivos> archivosCuadroViejo, Cuadro cuadroNuevo) {
        List<Archivos> archivos = new ArrayList<>();
        for (Archivos archivoViejo : archivosCuadroViejo){
            Archivos nuevoArchivo = new Archivos();
            nuevoArchivo.setCuadro(cuadroNuevo);
            nuevoArchivo.setGrupo(archivoViejo.getGrupo());
            nuevoArchivo.setNombre(archivoViejo.getNombre());
            nuevoArchivo.setTipo(archivoViejo.getTipo());
            archivosDao.addArchivos(nuevoArchivo);
            archivos.add(nuevoArchivo);
        }

        return archivos;

    }

    private Cuadro copiarDatosCuadro(Cuadro cuadro, Programacion nuevaProgramacion) {
        Cuadro nuevo = new Cuadro();
        nuevo.setProgramacion(nuevaProgramacion);
        nuevo.setDescripcion(cuadro.getDescripcion());
        nuevo.setNumero(cuadro.getNumero());
        nuevo.setFecha(nuevaProgramacion.getFecha());
        return nuevo;
    }

    private Programacion copiarDatosProgramaicon(Programacion programacionbyFecha, String razonProgramacion, List<Date> fechasRecords) {
        Programacion nueva = new Programacion();
        nueva.setTipoProgramacion(TipoProgramacion.D.toString());
        nueva.setModo(programacionbyFecha.getModo());
        nueva.setDescripcion(razonProgramacion);
        nueva.setTipoDia(programacionbyFecha.getTipoDia());
        nueva.setJornada(programacionbyFecha.getJornada());
        nueva.setFecha(fechasRecords.get(0));
        nueva.setDiasAplica(fechasRecords.size());
        nueva.setFechaDuplicado(programacionbyFecha.getFecha());
        nueva.setIdentificador(programacionbyFecha.getFecha().toString() +"/"+programacionbyFecha.getJornada());
        return nueva;
    }

    public boolean agregarProgramacionAFechas(List<Date> fechasRecords, Programacion nuevaProgramacion) {
        try{
            for(Date fecha:fechasRecords){
                FechasProg fechasProg = new FechasProg();
                fechasProg.setFecha(fecha);
                fechasProg.setProgramacion(nuevaProgramacion);
                fechasProgDao.addFechaProg(fechasProg);
            }
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return  false;
    }

    public List<Programacion> getAllProgramacionbyModo(String modo, Date fechaInicio, Date fechaFin) {
        return programacionDao.getProgramacionBetweenFechas(modo,fechaInicio,fechaFin);
    }
}
