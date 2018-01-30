package com.datawarehouse.model.servicios;

import com.datawarehouse.model.dao.ProgramacionDao;
import com.datawarehouse.model.entity.Programacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CargaDatosServicios")
public class CargaDatosServicios {

    @Autowired
    private ProgramacionDao programacionDao;

    public boolean existeProgramacion(String identificador) {
        Programacion programacion = programacionDao.encontrarProgramacion(identificador);
        if(programacion!=null) return true;
        return false;
    }

    public void agregarProgramacion(Programacion programacion) {
        programacionDao.addProgramacion(programacion);
    }
}
