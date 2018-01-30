package com.datawarehouse.model.servicios;

import com.datawarehouse.model.dao.ProgramacionDao;
import com.datawarehouse.model.entity.Archivos;
import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.view.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service("CargaDatosServicios")
public class CargaDatosServicios {

    @Autowired
    private ProgramacionDao programacionDao;

    @Autowired
    private ProcessorUtils processorUtils;

    @Autowired
    private CargaExpedicionesServicio cargaExpedicionesServicio;



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

    public List<LogDatos> cargarArchivoNuevo(Archivos archivo, List<LogDatos> logDatos, Programacion programacion) {
        if(archivo.getGrupo().equals(TipoArchivo.expediciones)){
          logDatos = cargaExpedicionesServicio.cargarArchivoExpediciones(archivo,logDatos,programacion);
        }else if(archivo.getGrupo().equals(TipoArchivo.buses)){

        }else if(archivo.getGrupo().equals(TipoArchivo.tracelog)){

        }else if(archivo.getGrupo().equals(TipoArchivo.distribuciones)){

        }else if(archivo.getGrupo().equals(TipoArchivo.tablaHorario)){

        }else if(archivo.getGrupo().equals(TipoArchivo.matrizDistancia)){

        }
        return logDatos;
    }
}
