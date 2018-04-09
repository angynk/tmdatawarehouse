package com.datawarehouse.view;

import com.datawarehouse.model.servicios.ExportarTotalesProcessor;
import com.datawarehouse.view.util.Consulta;
import com.datawarehouse.view.util.PathFiles;
import com.datawarehouse.view.util.TipoConsulta;
import com.datawarehouse.view.util.Util;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name="cAvanzadaBean")
@ViewScoped
public class ConsultasAvanzadasBean {

    private boolean parametrosVisibles;
    private boolean resultadosVisibles;
    private Date fechaInicio;
    private Date fechaFin;
    private String modo;
    private String tipoConsulta;
    private String consulta;
    private List<String> consultas;
    private List<String> modos;
    private List<String> tiposConsulta;
    private String archivoReporte;

    @ManagedProperty(value="#{ExportarTotalesProcessor}")
    private ExportarTotalesProcessor exportarTotalesProcessor;


    public ConsultasAvanzadasBean() {
    }

    @PostConstruct
    public void init() {
        parametrosVisibles = true;
        resultadosVisibles = false;
        modos = Util.listaModos();
        consultas = Util.listaConsultasAvanzadas();
        tiposConsulta = Util.listaTiposConsulta();
    }

    public void generarConsulta(){

        if(consulta.equals(Consulta.KM_VACIOS_OPERADOR)){
            archivoReporte = PathFiles.PATH_REPORTE_TOTAL_KM_VACIOS_OPERADOR;
        }else if(consulta.equals(Consulta.KM_VACIOS_SERVICIO)){
            archivoReporte = PathFiles.PATH_REPORTE_TOTAL_KM_VACIOS_SERVICIO;
        }

        if(tipoConsulta.equals(TipoConsulta.TOTALES)){
           archivoReporte = exportarTotalesProcessor.generarReporteTotalVaciosOperador(archivoReporte,fechaInicio,fechaFin,modo,consulta);
        }else {
            archivoReporte = exportarTotalesProcessor.generarReporteDiaADiaVaciosOperador(archivoReporte,fechaInicio,fechaFin,modo,consulta);
        }

        resultadosVisibles =true;
        parametrosVisibles = false;

    }

    public void descargar(){
        try {
            Util.descargarArchivo(archivoReporte,"ConsultaAvanzada.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isParametrosVisibles() {
        return parametrosVisibles;
    }

    public void setParametrosVisibles(boolean parametrosVisibles) {
        this.parametrosVisibles = parametrosVisibles;
    }

    public boolean isResultadosVisibles() {
        return resultadosVisibles;
    }

    public void setResultadosVisibles(boolean resultadosVisibles) {
        this.resultadosVisibles = resultadosVisibles;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public List<String> getModos() {
        return modos;
    }

    public void setModos(List<String> modos) {
        this.modos = modos;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public List<String> getTiposConsulta() {
        return tiposConsulta;
    }

    public void setTiposConsulta(List<String> tiposConsulta) {
        this.tiposConsulta = tiposConsulta;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public List<String> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<String> consultas) {
        this.consultas = consultas;
    }

    public ExportarTotalesProcessor getExportarTotalesProcessor() {
        return exportarTotalesProcessor;
    }

    public void setExportarTotalesProcessor(ExportarTotalesProcessor exportarTotalesProcessor) {
        this.exportarTotalesProcessor = exportarTotalesProcessor;
    }

    public String getArchivoReporte() {
        return archivoReporte;
    }

    public void setArchivoReporte(String archivoReporte) {
        this.archivoReporte = archivoReporte;
    }
}
