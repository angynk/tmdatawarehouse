package com.datawarehouse.view;

import com.datawarehouse.model.entity.Archivos;
import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.model.servicios.CargaDatosServicios;
import com.datawarehouse.view.util.LogDatos;
import com.datawarehouse.view.util.Util;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name="cargaDatosBean")
@ViewScoped
public class CargarDatosBean {

    private Date fechaInicio;
    private Date fechaFin;
    private String jornada;
    private String tipoDia;
    private String modo;
    private List<String> tiposDias;
    private List<String> modos;
    private List<Cuadro> cuadros;
    private String cuadro;
    private boolean creacionVisible;
    private boolean resultadosVisibles;
    private boolean incluirArchivosVisibles;
    private boolean archivosVisibles;
    private String identificador;
    private String progr;
    private List<Archivos> archivosLista;
    private List<String> tiposArchivo;
    private List<String> formatosArchivo;
    private List<String> programaciones;
    private Archivos nuevoArchivo;
    private UploadedFile file;
    private List<LogDatos> logDatos;
    private Programacion programacion;

    @ManagedProperty(value="#{CargaDatosServicios}")
    private CargaDatosServicios cargaDatosServicios;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public CargarDatosBean() {

    }

   public void buscarProgramacion(){
        if(fechaInicio!= null && fechaFin!= null){
            List<Programacion> programacionList = cargaDatosServicios.getProgramaciones(fechaInicio,fechaFin,tipoDia,Util.convertirModo(modo));
            for(Programacion p:programacionList){
                programaciones.add(p.getIdentificador());
            }
            if(programaciones.size()>0){
                incluirArchivosVisibles=true;
                creacionVisible=false;
                programacion = cargaDatosServicios.obtenerProgramacion(programaciones.get(0));
                cuadros = cargaDatosServicios.obtenerCuadrosProgramacion(programacion);
            }

        }
   }






    @PostConstruct
    public void init() {
        tiposDias = Util.listaDePeriocidad();
        creacionVisible = true;
        archivosLista = new ArrayList<Archivos>();
        tiposArchivo = Util.listaTipoArchivos();
        formatosArchivo = Util.listaFormatosArchivo();
        resultadosVisibles = false;
        incluirArchivosVisibles = false;
        archivosVisibles = false;
        logDatos = new ArrayList<>();
        programaciones = new ArrayList<>();
        modos = Util.listaModos();
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

    public List<String> getTiposDias() {
        return tiposDias;
    }

    public void setTiposDias(List<String> tiposDias) {
        this.tiposDias = tiposDias;
    }

    public boolean isCreacionVisible() {
        return creacionVisible;
    }

    public void setCreacionVisible(boolean creacionVisible) {
        this.creacionVisible = creacionVisible;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
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

    public List<Archivos> getArchivosLista() {
        return archivosLista;
    }

    public void setArchivosLista(List<Archivos> archivosLista) {
        this.archivosLista = archivosLista;
    }

    public Archivos getNuevoArchivo() {
        return nuevoArchivo;
    }

    public void setNuevoArchivo(Archivos nuevoArchivo) {
        this.nuevoArchivo = nuevoArchivo;
    }

    public List<String> getTiposArchivo() {
        return tiposArchivo;
    }

    public void setTiposArchivo(List<String> tiposArchivo) {
        this.tiposArchivo = tiposArchivo;
    }

    public List<String> getFormatosArchivo() {
        return formatosArchivo;
    }

    public void setFormatosArchivo(List<String> formatosArchivo) {
        this.formatosArchivo = formatosArchivo;
    }


    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<LogDatos> getLogDatos() {
        return logDatos;
    }

    public void setLogDatos(List<LogDatos> logDatos) {
        this.logDatos = logDatos;
    }

    public boolean isResultadosVisibles() {
        return resultadosVisibles;
    }

    public void setResultadosVisibles(boolean resultadosVisibles) {
        this.resultadosVisibles = resultadosVisibles;
    }

    public boolean isIncluirArchivosVisibles() {
        return incluirArchivosVisibles;
    }

    public void setIncluirArchivosVisibles(boolean incluirArchivosVisibles) {
        this.incluirArchivosVisibles = incluirArchivosVisibles;
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

    public Programacion getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Programacion programacion) {
        this.programacion = programacion;
    }

    public String getProgr() {
        return progr;
    }

    public void setProgr(String progr) {
        this.progr = progr;
    }

    public List<String> getProgramaciones() {
        return programaciones;
    }

    public void setProgramaciones(List<String> programaciones) {
        this.programaciones = programaciones;
    }

    public boolean isArchivosVisibles() {
        return archivosVisibles;
    }

    public void setArchivosVisibles(boolean archivosVisibles) {
        this.archivosVisibles = archivosVisibles;
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
