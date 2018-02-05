package com.datawarehouse.model.servicios;

import com.datawarehouse.model.dao.TablaHorarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargaTablaHorarioServicio {

    @Autowired
    private TablaHorarioDao tablaHorarioDao;


}
