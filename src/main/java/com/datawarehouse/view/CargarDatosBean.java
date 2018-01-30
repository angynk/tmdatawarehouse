package com.datawarehouse.view;

import com.datawarehouse.model.entity.Archivos;
import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.model.servicios.CargaDatosServicios;
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

    private Date fecha;
    private String jornada;
    private String tipoDia;
    private List<String> tiposDias;
    private boolean creacionVisible;
    private String identificador;
    private List<Archivos> archivosLista;
    private List<String> tiposArchivo;
    private List<String> formatosArchivo;
    private Archivos nuevoArchivo;


    @ManagedProperty(value="#{CargaDatosServicios}")
    private CargaDatosServicios cargaDatosServicios;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public CargarDatosBean() {

    }

    public void crearProgramacion(){
        if(datosCompletos()){
            identificador = calcularIdentificador();
            if(!cargaDatosServicios.existeProgramacion(identificador)){
                Programacion programacion = new Programacion();
                programacion.setFecha(fecha);
                programacion.setIdentificador(identificador);
                programacion.setJornada(jornada);
                programacion.setTipoDia(tipoDia);
                cargaDatosServicios.agregarProgramacion(programacion);
                creacionVisible = false;
            }else{
                messagesView.error("Ya existe información para esa fecha","Completar correctamente el formulario");
            }
        }else{
            messagesView.error("Campos incompletos","Completar correctamente el formulario");
        }


    }

    public void cargarArchivo(FileUploadEvent event){
        UploadedFile file = event.getFile();
        if(file!=null){
            try {
                String nombre = cargaDatosServicios.copyFile(file.getFileName(),file.getInputstream());
                nuevoArchivo.setNombre(nombre);
            } catch (IOException e) {
                messagesView.error("Error en la carga del archivo",e.getMessage());
            }
        }


    }

    public void agregarArchivo(){
        nuevoArchivo = new Archivos();
    }

    public void guardarArchivo(){
        archivosLista.add(nuevoArchivo);
    }

    public void cancelar(){

    }

    private String calcularIdentificador() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        return tipoDia+"/"+sdfDate.format(fecha)+"/"+jornada;
    }

    private boolean datosCompletos() {
        if(fecha!=null && jornada!=null ) return true;
        return false;
    }

    @PostConstruct
    public void init() {
        tiposDias = Util.listaDePeriocidad();
        creacionVisible = true;
        archivosLista = new ArrayList<Archivos>();
        tiposArchivo = Util.listaTipoArchivos();
        formatosArchivo = Util.listaFormatosArchivo();
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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


}
