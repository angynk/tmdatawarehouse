package com.datawarehouse.model.servicios;

import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.Programacion;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportarExpedicionesProcessor {

    @Autowired
    private CargaExpedicionesServicio cargaExpedicionesServicio;




    public void generarConsultaExpediciones(String consultaPath, Programacion programacion, Cuadro cuadroObj) {
            cargaExpedicionesServicio.getExpedicionesCuadro(cuadroObj,consultaPath);
    }

}
