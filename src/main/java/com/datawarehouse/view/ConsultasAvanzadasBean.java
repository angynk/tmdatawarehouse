package com.datawarehouse.view;

import com.datawarehouse.view.util.Util;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
    private List<String> modos;


    public ConsultasAvanzadasBean() {
    }

    @PostConstruct
    public void init() {
        parametrosVisibles = true;
        resultadosVisibles = false;
        modos = Util.listaModos();
    }

    public void generarConsulta(){

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
}
