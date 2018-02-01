package com.datawarehouse.view;

import com.datawarehouse.model.entity.Archivos;
import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.model.servicios.CargaDatosServicios;
import com.datawarehouse.view.util.TipoArchivo;
import com.datawarehouse.view.util.Util;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ManagedBean(name="pBean")
@ViewScoped
public class CrearProgBean {

    private Date fecha;
    private String jornada;
    private String tipoDia;
    private Programacion nuevaProgramacion;
    private String identificador;
    private List<String> tiposDias;
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
        creacionVisible = true;
        incluirArchivosVisibles = false;
    }

    public void crearProgramacion(){
        if(datosCompletos()){
            identificador = calcularIdentificador();
            if(!cargaDatosServicios.existeProgramacion(identificador)){
                nuevaProgramacion = new Programacion();
                nuevaProgramacion.setIdentificador(identificador);
                nuevaProgramacion.setFecha(fecha);
                nuevaProgramacion.setJornada(jornada);
                nuevaProgramacion.setTipoDia(tipoDia);
                cargaDatosServicios.agregarProgramacion(nuevaProgramacion);
                creacionVisible = false;
                incluirArchivosVisibles = true;
            }else{
                messagesView.error("Ya existe información para esa fecha","Completar correctamente el formulario");
            }
        }else{
            messagesView.error("Campos incompletos","Completar correctamente el formulario");
        }
    }

    private boolean datosCompletos() {
        if(fecha!=null && jornada!=null ) return true;
        return false;
    }


    private String calcularIdentificador() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        return tipoDia+"/"+sdfDate.format(fecha)+"/"+jornada;
    }

    public void cargarExpediciones(){
        if(file!=null){
            try {
                String nombre = cargaDatosServicios.copyFile(file.getFileName(),file.getInputstream());
                nombre = cargaDatosServicios.incluirFilaArchivo(nombre,nuevaProgramacion,tipo);
                cargaDatosServicios.cargarArchivoExpediciones(nombre);
                cargaDatosServicios.eliminarDatosTemporales(nuevaProgramacion);
                incluirArchivosVisibles = false;
                messagesView.info("Proceso Exitoso ","Nueva programación creada");
                Archivos archivo = new Archivos();
                archivo.setNombre(file.getFileName());
                archivo.setGrupo(TipoArchivo.expediciones);
                archivo.setTipo(tipo);
                archivo.setProgramacion(nuevaProgramacion);
                cargaDatosServicios.agregarArchivo(archivo);
            } catch (IOException e) {
                messagesView.error("Error en la carga del archivo",e.getMessage());
            } catch (Exception e) {
                messagesView.error("Error en la carga del archivo",e.getMessage());
            }
        }
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
}
