package com.datawarehouse.model.servicios;

import com.datawarehouse.model.dao.ProgramacionDao;
import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.view.util.PathFiles;
import com.datawarehouse.view.util.ProcessorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service("CargaDatosServicios")
public class CargaDatosServicios {

    @Autowired
    private ProgramacionDao programacionDao;

    @Autowired
    private ProcessorUtils processorUtils;

    private static String destination = PathFiles.PATH;

    public boolean existeProgramacion(String identificador) {
        Programacion programacion = programacionDao.encontrarProgramacion(identificador);
        if(programacion!=null) return true;
        return false;
    }

    public void agregarProgramacion(Programacion programacion) {
        programacionDao.addProgramacion(programacion);
    }

    public String copyFile(String fileName, InputStream in){
        processorUtils.copyFile(fileName,in,destination);
        return fileName;
    }
}
