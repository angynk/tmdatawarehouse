package com.datawarehouse.view;

import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.model.servicios.CargaDatosServicios;
import com.datawarehouse.view.util.PathFiles;
import com.datawarehouse.view.util.TipoArchivo;
import com.datawarehouse.view.util.Util;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name="cBasicaBean")
@ViewScoped
public class ConsultasBasicasBean {

    private boolean creacionVisible;
    private Date fechaInicio;
    private Date fechaFin;
    private String tipoDia;
    private List<String> tiposDias;
    private String modo;
    private String tipoConsulta;
    private List<String> tiposConsulta;
    private List<String> modos;
    private List<String> programaciones;
    private String progr;
    private String consultaPath;
    private List<Cuadro> cuadrosLista;
    private List<Cuadro> cuadros;
    private String cuadro;
    private Programacion programacion;
    private boolean incluirArchivosVisibles;
    private boolean cuadrosVisibles;
    private boolean descargaVisible;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    @ManagedProperty(value="#{CargaDatosServicios}")
    private CargaDatosServicios cargaDatosServicios;

    public ConsultasBasicasBean() {
    }

    @PostConstruct
    public void init() {
        tiposDias = Util.listaDePeriocidad();
        creacionVisible = true;
        descargaVisible = false;
        incluirArchivosVisibles = false;
        programaciones = new ArrayList<>();
        modos = Util.listaModos();
        tiposConsulta = Util.listaConsultasBasicas();

    }

    public void buscarProgramacion(){
        if(fechaInicio!= null && fechaFin!= null){
            List<Programacion> programacionList = cargaDatosServicios.getProgramaciones(fechaInicio,fechaFin,tipoDia, Util.convertirModo(modo));
            for(Programacion p:programacionList){
                programaciones.add(p.getIdentificador());
            }
            incluirArchivosVisibles=true;
            creacionVisible=false;

            programacion = cargaDatosServicios.obtenerProgramacion(programaciones.get(0));
            cuadros = cargaDatosServicios.obtenerCuadrosProgramacion(programacion);

        }
    }

    public void generarConsulta(){
        if(tipoConsulta.equals(TipoArchivo.expediciones)){
            consultaPath = PathFiles.PATH_EXPEDICIONES;
            cargaDatosServicios.generarConsultaExpediciones(consultaPath,programacion,cuadro);
        }else if (tipoConsulta.equals(TipoArchivo.buses)){
            consultaPath = PathFiles.PATH_BUSES;
            cargaDatosServicios.generarConsultaBuses(consultaPath,programacion,cuadro);
        }else if (tipoConsulta.equals(TipoArchivo.distribuciones)){
            consultaPath = PathFiles.PATH_DISTRIBUCIONES;
            cargaDatosServicios.generarConsultaDistribuciones(consultaPath,programacion,cuadro);
        }else if (tipoConsulta.equals(TipoArchivo.tablaHorario)){
            consultaPath = PathFiles.PATH_IPH;
            cargaDatosServicios.generarConsultaTablaHorario(consultaPath,programacion,cuadro);
        }

        descargaVisible = true;
    }

    public void seleccionarProgramacion(){
        cuadrosVisibles = true;
        incluirArchivosVisibles=false;
        programacion = cargaDatosServicios.obtenerProgramacion(progr);
        cuadrosLista = cargaDatosServicios.obtenerCuadrosProgramacion(programacion);
    }

    public void descargar(){
        String path = consultaPath;
        try {
            Util.descargarArchivo(path,"ReporteDatos.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateCuadros(){
        programacion = cargaDatosServicios.obtenerProgramacion(progr);
        cuadros = cargaDatosServicios.obtenerCuadrosProgramacion(programacion);
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

    public String getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia) {
        this.tipoDia = tipoDia;
    }

    public List<String> getTiposDias() {
        return tiposDias;
    }

    public void setTiposDias(List<String> tiposDias) {
        this.tiposDias = tiposDias;
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

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }

    public CargaDatosServicios getCargaDatosServicios() {
        return cargaDatosServicios;
    }

    public void setCargaDatosServicios(CargaDatosServicios cargaDatosServicios) {
        this.cargaDatosServicios = cargaDatosServicios;
    }

    public String getProgr() {
        return progr;
    }

    public void setProgr(String progr) {
        this.progr = progr;
    }

    public boolean isCuadrosVisibles() {
        return cuadrosVisibles;
    }

    public void setCuadrosVisibles(boolean cuadrosVisibles) {
        this.cuadrosVisibles = cuadrosVisibles;
    }

    public List<Cuadro> getCuadrosLista() {
        return cuadrosLista;
    }

    public void setCuadrosLista(List<Cuadro> cuadrosLista) {
        this.cuadrosLista = cuadrosLista;
    }

    public Programacion getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Programacion programacion) {
        this.programacion = programacion;
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

    public String getConsultaPath() {
        return consultaPath;
    }

    public void setConsultaPath(String consultaPath) {
        this.consultaPath = consultaPath;
    }

    public boolean isDescargaVisible() {
        return descargaVisible;
    }

    public void setDescargaVisible(boolean descargaVisible) {
        this.descargaVisible = descargaVisible;
    }

    public List<Cuadro> getCuadros() {
        return cuadros;
    }

    public void setCuadros(List<Cuadro> cuadros) {
        this.cuadros = cuadros;
    }

    public String getCuadro() {
        return cuadro;
    }

    public void setCuadro(String cuadro) {
        this.cuadro = cuadro;
    }
}
