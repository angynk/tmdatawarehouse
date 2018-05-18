package com.datawarehouse.model.servicios;

import com.datawarehouse.model.dao.IndicadoresDao;
import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.Indicadores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CalcularIndicadoresServicio")
public class CalcularIndicadoresServicio {

    @Autowired
    private IndicadoresDao indicadoresDao;

    public CalcularIndicadoresServicio() {
    }

    public void calcularIndicadoresExpediciones(Cuadro nuevoCuadro) {
        Indicadores indicadores = new Indicadores();

        //Calcular Numero de buses
        indicadores.setNumBuses(indicadoresDao.getNumeroBuses(nuevoCuadro));
        indicadores.setKmComerciales(indicadoresDao.getKilometros(nuevoCuadro,"Exp"));
        indicadores.setKmVacioVEX(indicadoresDao.getKilometros(nuevoCuadro,"Vexp"));
        indicadores.setKmVacioVPA(indicadoresDao.getKilometros(nuevoCuadro,"Vpa"));
        indicadores.setKmVacioVH(indicadoresDao.getKilometros(nuevoCuadro,"Vh"));
        indicadores.setKmVacioTotal(calcularSumaVacio(indicadores));
        indicadores.setPorcentajeVacio(calcularPorcentajeVacio(indicadores));

        indicadoresDao.addIndicador(indicadores);
    }

    private Double calcularPorcentajeVacio(Indicadores indicadores) {
        Double porcentaje = indicadores.getKmVacioTotal()*100;
        porcentaje = porcentaje / indicadores.getKmComerciales();
        return porcentaje;
    }

    private Double calcularSumaVacio(Indicadores indicadores) {
        return indicadores.getKmVacioVEX() + indicadores.getKmVacioVH()+indicadores.getKmVacioVPA();
    }
}
