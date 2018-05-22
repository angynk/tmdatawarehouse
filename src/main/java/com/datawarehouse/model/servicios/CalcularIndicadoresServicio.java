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
        indicadores.setKmVacioVEX(indicadoresDao.getKilometros(nuevoCuadro,"VEx"));
        indicadores.setKmVacioVPA(indicadoresDao.getKilometros(nuevoCuadro,"VPa"));
        indicadores.setKmVacioVH(indicadoresDao.getKilometros(nuevoCuadro,"VIn"));
        indicadores.setKmVacioTotal(calcularSumaVacio(indicadores));
        indicadores.setPorcentajeVacio(calcularPorcentajeVacio(indicadores));
        indicadores.setCuadro(nuevoCuadro);

        indicadoresDao.addIndicador(indicadores);
    }

    private Double calcularPorcentajeVacio(Indicadores indicadores) {
        Double porcentaje = indicadores.getKmVacioTotal()*100;
        porcentaje = porcentaje / indicadores.getKmComerciales();
        porcentaje = Math.floor(porcentaje * 100) / 100;
        return porcentaje;
    }

    private Double calcularSumaVacio(Indicadores indicadores) {
        return indicadores.getKmVacioVEX() + indicadores.getKmVacioVH()+indicadores.getKmVacioVPA();
    }
}
