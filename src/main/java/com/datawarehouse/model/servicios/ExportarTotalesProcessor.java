package com.datawarehouse.model.servicios;

import com.datawarehouse.model.dao.KilometrosVaciosDao;
import com.datawarehouse.view.util.Consulta;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("ExportarTotalesProcessor")
public class ExportarTotalesProcessor {


    @Autowired
    private KilometrosVaciosDao kilometrosVaciosDao;


    public String generarReporteTotalVaciosOperador(String archivoReporte, Date fechaInicio, Date fechaFin, String modo, String consulta) {
        if(consulta.equals(Consulta.KM_VACIOS_OPERADOR)){
            archivoReporte = archivoReporte+"-"+RandomStringUtils.randomAlphanumeric(3)+".xls";
            kilometrosVaciosDao.generarReporteTotalVaciosOperador(archivoReporte, fechaInicio,fechaFin);
        }
        return archivoReporte;
    }

    public String generarReporteDiaADiaVaciosOperador(String archivoReporte, Date fechaInicio, Date fechaFin, String modo, String consulta) {
        archivoReporte = archivoReporte+"-"+RandomStringUtils.randomAlphanumeric(3)+".xls";

        if(consulta.equals(Consulta.KM_VACIOS_OPERADOR)){
            kilometrosVaciosDao.generarReporteDiaADiaVaciosOperador(archivoReporte,fechaInicio,fechaFin);
        }else if(consulta.equals(Consulta.KM_VACIOS_SERVICIO)){
            kilometrosVaciosDao.generarReporteDiaADiaVaciosServicio(archivoReporte,fechaInicio,fechaFin);
        }
        return archivoReporte;
    }
}
