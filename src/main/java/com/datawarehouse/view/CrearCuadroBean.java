package com.datawarehouse.view;

import com.datawarehouse.model.entity.Archivos;
import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.model.servicios.CargaDatosServicios;
import com.datawarehouse.view.util.Util;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name="cBean")
@ViewScoped
public class CrearCuadroBean {

    private boolean creacionVisible;
    private Date fechaInicio;
    private Date fechaFin;
    private String jornada;
    private String tipoDia;
    private String progr;
    private String modo;
    private List<String> tiposDias;
    private List<String> modos;
    private List<String> programaciones;
    private boolean incluirArchivosVisibles;
    private boolean archivosVisibles;

    @ManagedProperty(value="#{CargaDatosServicios}")
    private CargaDatosServicios cargaDatosServicios;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public CrearCuadroBean() {
    }

    public void buscarProgramacion(){
        if(fechaInicio!= null && fechaFin!= null){
            List<Programacion> programacionList = cargaDatosServicios.getProgramaciones(fechaInicio,fechaFin,tipoDia, Util.convertirModo(modo));
            for(Programacion p:programacionList){
                programaciones.add(p.getIdentificador());
            }
            incluirArchivosVisibles=true;
            creacionVisible=false;

        }
    }

    public void seleccionarProgramacion(){
        archivosVisibles = true;
//        archivosLista = cargaDatosServicios.obtenerArchivosLista(progr);
//        programacion = cargaDatosServicios.obtenerProgramacion(progr);

    }

    @PostConstruct
    public void init() {
        tiposDias = Util.listaDePeriocidad();
        creacionVisible = true;
        incluirArchivosVisibles = false;
        programaciones = new ArrayList<>();
        modos = Util.listaModos();
    }

    public boolean isCreacionVisible() {
        return creacionVisible;
    }

    public void setCreacionVisible(boolean creacionVisible) {
        this.creacionVisible = creacionVisible;
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

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public String getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia) {
        this.tipoDia = tipoDia;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public List<String> getTiposDias() {
        return tiposDias;
    }

    public void setTiposDias(List<String> tiposDias) {
        this.tiposDias = tiposDias;
    }

    public List<String> getModos() {
        return modos;
    }

    public void setModos(List<String> modos) {
        this.modos = modos;
    }

    public List<String> getProgramaciones() {
        return programaciones;
    }

    public void setProgramaciones(List<String> programaciones) {
        this.programaciones = programaciones;
    }

    public boolean isIncluirArchivosVisibles() {
        return incluirArchivosVisibles;
    }

    public void setIncluirArchivosVisibles(boolean incluirArchivosVisibles) {
        this.incluirArchivosVisibles = incluirArchivosVisibles;
    }

    public CargaDatosServicios getCargaDatosServicios() {
        return cargaDatosServicios;
    }

    public void setCargaDatosServicios(CargaDatosServicios cargaDatosServicios) {
        this.cargaDatosServicios = cargaDatosServicios;
    }

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }

    public String getProgr() {
        return progr;
    }

    public void setProgr(String progr) {
        this.progr = progr;
    }

    public boolean isArchivosVisibles() {
        return archivosVisibles;
    }

    public void setArchivosVisibles(boolean archivosVisibles) {
        this.archivosVisibles = archivosVisibles;
    }
}
