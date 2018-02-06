package com.datawarehouse.model.servicios;

import com.datawarehouse.model.dao.TablaHorarioDao;
import com.datawarehouse.model.entity.Archivos;
import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.view.util.LogDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargaTablaHorarioServicio {

    @Autowired
    private TablaHorarioDao tablaHorarioDao;


    public List<LogDatos> agregarInformacionTablaHorario(Programacion programacion, Archivos archivo, List<LogDatos> logDatos) {

        return null;
    }
}
