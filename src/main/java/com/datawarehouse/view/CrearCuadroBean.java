package com.datawarehouse.view;

import com.datawarehouse.model.entity.Archivos;
import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.model.servicios.CargaDatosServicios;
import com.datawarehouse.view.util.TipoArchivo;
import com.datawarehouse.view.util.Util;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
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
    private UploadedFile file;
    private List<String> tiposDias;
    private List<String> modos;
    private List<String> programaciones;
    private List<Cuadro> cuadrosLista;
    private Cuadro nuevoCuadro;
    private Programacion programacion;
    private boolean incluirArchivosVisibles;
    private boolean cuadrosVisibles;
    private boolean archivosVisibles;
    private List<String> formatosArchivo;
    private String tipo;

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
        cuadrosVisibles = true;
        programacion = cargaDatosServicios.obtenerProgramacion(progr);
        cuadrosLista = cargaDatosServicios.obtenerCuadrosProgramacion(programacion);
    }

    public void crearCuadro(){
            nuevoCuadro = new Cuadro();
            nuevoCuadro.setProgramacion(programacion);
    }

    public void guardarCuadro(){

            cargaDatosServicios.guardarCuadro(nuevoCuadro);
            cargarExpediciones();
    }

    public void cargarExpediciones(){
        if(file!=null){
            try {
                String nombre = cargaDatosServicios.copyFile(file.getFileName(),file.getInputstream());
                nombre = cargaDatosServicios.incluirFilaArchivo(nombre,programacion,tipo,Util.convertirModo(modo),nuevoCuadro.getNumero());
                cargaDatosServicios.cargarArchivoExpediciones(nombre);
                cargaDatosServicios.eliminarDatosTemporales(programacion);
                incluirArchivosVisibles = false;
                messagesView.info("Proceso Exitoso ","Nueva programación creada");
                Archivos archivo = new Archivos();
                archivo.setNombre(file.getFileName());
                archivo.setGrupo(TipoArchivo.expediciones);
                archivo.setTipo(tipo);
                archivo.setProgramacion(programacion);
                cargaDatosServicios.agregarArchivo(archivo);
            } catch (IOException e) {
                messagesView.error("Error en la carga del archivo",e.getMessage());
            } catch (Exception e) {
                messagesView.error("Error en la carga del archivo",e.getMessage());
            }
        }
    }

    public void cancelar(){

    }

    @PostConstruct
    public void init() {
        tiposDias = Util.listaDePeriocidad();
        creacionVisible = true;
        incluirArchivosVisibles = false;
        programaciones = new ArrayList<>();
        formatosArchivo = Util.listaFormatosCSV();
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

    public Cuadro getNuevoCuadro() {
        return nuevoCuadro;
    }

    public void setNuevoCuadro(Cuadro nuevoCuadro) {
        this.nuevoCuadro = nuevoCuadro;
    }

    public List<String> getFormatosArchivo() {
        return formatosArchivo;
    }

    public void setFormatosArchivo(List<String> formatosArchivo) {
        this.formatosArchivo = formatosArchivo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Programacion getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Programacion programacion) {
        this.programacion = programacion;
    }
}
