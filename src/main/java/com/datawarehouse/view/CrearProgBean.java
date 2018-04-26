package com.datawarehouse.view;

import com.datawarehouse.model.entity.Archivos;
import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.model.servicios.CargaDatosServicios;
import com.datawarehouse.view.util.*;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ManagedBean(name="pBean")
@ViewScoped
public class CrearProgBean {

    private Date fecha;
    private String fechas;
    private String jornada;
    private String tipoDia;
    private String descripcion;
    private String modo;
    private Programacion nuevaProgramacion;
    private String identificador;
    private List<String> tiposDias;
    private List<String> modos;
    private UploadedFile file;
    private boolean creacionVisible;

    private boolean incluirArchivosVisibles;
    private List<String> formatosArchivo;
    private String tipo;

    @ManagedProperty(value="#{CargaDatosServicios}")
    private CargaDatosServicios cargaDatosServicios;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;


    public CrearProgBean() {
    }

    @PostConstruct
    public void init() {
        nuevaProgramacion = new Programacion();
        formatosArchivo = Util.listaFormatosCSV();
        tiposDias = Util.listaDePeriocidad();
        modos = Util.listaModos();
        creacionVisible = true;
        incluirArchivosVisibles = false;
    }

    public void crearProgramacion(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        fechas = ec.getRequestParameterMap().get("fechas");
        if(datosCompletos()){
            List<Date> fechasRecords = ProcessorUtils.convertirAfechas(fechas);
            identificador = calcularIdentificador();
            if(!cargaDatosServicios.existeProgramacion(identificador) && !cargaDatosServicios.existeProgramacionFechas(fechasRecords)){
                nuevaProgramacion = new Programacion();
                nuevaProgramacion.setIdentificador(identificador);
                nuevaProgramacion.setFecha(fecha);
                nuevaProgramacion.setJornada(jornada);
                nuevaProgramacion.setTipoDia(tipoDia);
                nuevaProgramacion.setDescripcion(descripcion);
                nuevaProgramacion.setModo(Util.convertirModo(modo));
                nuevaProgramacion.setDiasAplica(fechasRecords.size());
                nuevaProgramacion.setTipoProgramacion(TipoProgramacion.N.toString());
                cargaDatosServicios.agregarProgramacion(nuevaProgramacion);

               if( cargaDatosServicios.agregarProgramacionAFechas(fechasRecords,nuevaProgramacion)){
                   creacionVisible = false;
                   incluirArchivosVisibles = true;
                   messagesView.info("Proceso Exitoso","La progrmación ha sido creada");
               }else{
                   messagesView.error("Ya existe información para esa fecha","Completar correctamente el formulario");
               }
            }else{
                messagesView.error("Ya existe información para esa fecha","Completar correctamente el formulario");
            }
        }else{
            messagesView.error("Campos incompletos","Completar correctamente el formulario");
        }
    }




    private boolean datosCompletos() {
        if(fecha!=null && jornada!=null && descripcion!=null && fechas!=null) return true;
        return false;
    }


    private String calcularIdentificador() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        return sdfDate.format(fecha)+"/"+jornada;
    }



    public Programacion getNuevaProgramacion() {
        return nuevaProgramacion;
    }

    public void setNuevaProgramacion(Programacion nuevaProgramacion) {
        this.nuevaProgramacion = nuevaProgramacion;
    }

    public List<String> getTiposDias() {
        return tiposDias;
    }

    public void setTiposDias(List<String> tiposDias) {
        this.tiposDias = tiposDias;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public boolean isCreacionVisible() {
        return creacionVisible;
    }

    public void setCreacionVisible(boolean creacionVisible) {
        this.creacionVisible = creacionVisible;
    }

    public boolean isIncluirArchivosVisibles() {
        return incluirArchivosVisibles;
    }

    public void setIncluirArchivosVisibles(boolean incluirArchivosVisibles) {
        this.incluirArchivosVisibles = incluirArchivosVisibles;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getFechas() {
        return fechas;
    }

    public void setFechas(String fechas) {
        this.fechas = fechas;
    }
}
