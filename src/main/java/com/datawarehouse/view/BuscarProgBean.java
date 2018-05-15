package com.datawarehouse.view;

import com.datawarehouse.model.entity.Archivos;
import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.model.servicios.CargaDatosServicios;
import com.datawarehouse.view.util.LogDatos;
import com.datawarehouse.view.util.TipoArchivo;
import com.datawarehouse.view.util.Util;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name="buscarProgBean")
@SessionScoped
public class BuscarProgBean {

    private Date fechaInicio;
    private Date fechaFin;
    private String modo;
    private String cuadroInformacion;
    private List<String> modos;
    private List<Programacion> programacionList;
    private Programacion programacionSelected;
    private List<Cuadro> cuadroList;
    private Cuadro cuadro;
    private Cuadro nuevoCuadro;
    private List<String> formatosArchivo;
    private String tipo;
    private String tipobuses;
    private UploadedFile file;
    private UploadedFile fileBuses;
    private List<Archivos> archivosLista;
    private boolean resultadosVisibles;
    private Archivos nuevoArchivo;
    private Archivos selectedArchivo;
    private List<LogDatos> logDatos;
    private boolean visibleCargaArchivos;
    private List<String> tiposArchivo;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    @ManagedProperty(value="#{CargaDatosServicios}")
    private CargaDatosServicios cargaDatosServicios;

    public BuscarProgBean() {
           programacionList = new ArrayList<>();
           cuadroList = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        modos = Util.listaModos();
        formatosArchivo = Util.listaFormatosCSV();
        archivosLista = new ArrayList<Archivos>();
        tiposArchivo = Util.listaTipoArchivos();
    }

    public void verCuadro(){
        cuadroList = cargaDatosServicios.obtenerCuadrosProgramacion(programacionSelected);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/listaCuadros.xhtml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public void volver(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/listaCuadros.xhtml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void atras(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/buscarProgramacion.xhtml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void cancelarCreacionArchivo(){
        visibleCargaArchivos = false;
    }

    public void habilitarNuevoCuadro(){
        nuevoCuadro = new Cuadro();
        nuevoCuadro.setProgramacion(programacionSelected);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/NuevoCuadro.xhtml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void guardarCuadro(){
        if(datosCuadroCompletos()){
            if(file!=null){
                if(fileBuses!=null){
                    if(cargaDatosServicios.noExisteElNumeroDeCuadro(nuevoCuadro.getNumero())){
                        nuevoCuadro.setFecha(programacionSelected.getFecha());
                        cargaDatosServicios.guardarCuadro(nuevoCuadro);
                        cargarExpediciones();
                        cargarBuses();
                        verCuadro();
                    }else{
                        messagesView.error("Operación fallida","Ya existe un cuadro con ese número");
                    }

                }else{
                    messagesView.error("Operación fallida","Debe adjuntar el archivo de buses");
                }

            }else{
                messagesView.error("Operación fallida","Debe adjuntar el archivo de expediciones");
            }

        }else{
            messagesView.error("Operación fallida","Complete todos los campos para crear un Cuadro Nuevo");
        }
    }

    private void cargarBuses() {
        if(fileBuses!=null){
            try {
                List<LogDatos> logDatos = new ArrayList<>();


                String nombre = cargaDatosServicios.copyFile(fileBuses.getFileName(),fileBuses.getInputstream());
                Archivos archivo = new Archivos();
                archivo.setNombre(fileBuses.getFileName());
                archivo.setGrupo(TipoArchivo.buses);
                archivo.setTipo(tipobuses);
                archivo.setCuadro(nuevoCuadro);
                cargaDatosServicios.agregarInformacionBuses(programacionSelected, archivo, logDatos,nuevoCuadro);
                messagesView.info("Proceso Exitoso ","Nuevo Cuadro creado");
            } catch (IOException e) {
                messagesView.error("Error en la carga del archivo",e.getMessage());
            } catch (Exception e) {
                messagesView.error("Error en la carga del archivo",e.getMessage());
            }
        }
    }

    private boolean datosCuadroCompletos() {

        if(nuevoCuadro!=null){
            if (nuevoCuadro.getNumero()!=null && nuevoCuadro.getDescripcion()!=null){
                return true;
            }
        }
        return false;
    }

    public void cancelar(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/listaCuadros.xhtml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void cargarExpediciones(){
        if(file!=null){
            try {
                String nombre = cargaDatosServicios.copyFile(file.getFileName(),file.getInputstream());
                nombre = cargaDatosServicios.incluirFilaArchivo(nombre,programacionSelected,tipo,Util.convertirModo(modo),nuevoCuadro.getNumero());
                cargaDatosServicios.cargarArchivoExpediciones(nombre);
                cargaDatosServicios.eliminarDatosTemporales(programacionSelected);
                messagesView.info("Proceso Exitoso ","Nuevo Cuadro creado");
                Archivos archivo = new Archivos();
                archivo.setNombre(file.getFileName());
                archivo.setGrupo(TipoArchivo.expediciones);
                archivo.setTipo(tipo);
                archivo.setCuadro(nuevoCuadro);
                cargaDatosServicios.agregarArchivo(archivo);
            } catch (IOException e) {
                messagesView.error("Error en la carga del archivo",e.getMessage());
            } catch (Exception e) {
                messagesView.error("Error en la carga del archivo",e.getMessage());
            }
        }
    }


    public void buscarProgramacion(){
        if(fechaInicio!= null && fechaFin!= null){
            programacionList = cargaDatosServicios.getProgramaciones(fechaInicio,fechaFin,Util.convertirModo(modo));
        }
    }

    public void verDetalleCuadro(){
        archivosLista = cargaDatosServicios.obtenerArchivosLista(cuadro);
        cuadroInformacion = "Información Cuadro No."+cuadro.getNumero();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/cargarDatos.xhtml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void cargaMasivaDatos(){
        if(archivosLista.size()>0 && cuadro!=null){
            Cuadro cuadroProg = cargaDatosServicios.obtenerCuadro(programacionSelected,cuadro.getNumero());
            for(Archivos archivo: archivosLista){
                if(!archivo.isAdjuntado()){
                    archivo.setCuadro(cuadroProg);
                    logDatos= cargaDatosServicios.cargarArchivoNuevo(archivo,logDatos,programacionSelected,cuadroProg);
                }
            }
            resultadosVisibles = false;
            messagesView.info("Operación exitosa","Se ha cargado la datos asociados al cuadro de la programación");
        }else{
            messagesView.error("No hay archivos asociados a la programacion","Verificar datos");
        }
    }

    public void eliminarDatosArchivo(){
        cargaDatosServicios.eliminarDatosArchivo(selectedArchivo);
        verDetalleCuadro();
        messagesView.info("Operación exitosa","Datos Eliminados");
    }

    public void agregarArchivo(){
        nuevoArchivo = new Archivos();
        visibleCargaArchivos = true;
    }

    public void guardarArchivo(){
        if(file!=null){
            try {
                String nombre = cargaDatosServicios.copyFile(file.getFileName(),file.getInputstream());
                nuevoArchivo.setNombre(nombre);
                nuevoArchivo.setAdjuntado(false);
                archivosLista.add(nuevoArchivo);
            } catch (IOException e) {
                messagesView.error("Error en la carga del archivo",e.getMessage());
            }
        }

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

    public List<Programacion> getProgramacionList() {
        return programacionList;
    }

    public void setProgramacionList(List<Programacion> programacionList) {
        this.programacionList = programacionList;
    }

    public Programacion getProgramacionSelected() {
        return programacionSelected;
    }

    public void setProgramacionSelected(Programacion programacionSelected) {
        this.programacionSelected = programacionSelected;
    }

    public List<Cuadro> getCuadroList() {
        return cuadroList;
    }

    public void setCuadroList(List<Cuadro> cuadroList) {
        this.cuadroList = cuadroList;
    }

    public Cuadro getCuadro() {
        return cuadro;
    }

    public void setCuadro(Cuadro cuadro) {
        this.cuadro = cuadro;
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

    public List<Archivos> getArchivosLista() {
        return archivosLista;
    }

    public void setArchivosLista(List<Archivos> archivosLista) {
        this.archivosLista = archivosLista;
    }

    public boolean isResultadosVisibles() {
        return resultadosVisibles;
    }

    public void setResultadosVisibles(boolean resultadosVisibles) {
        this.resultadosVisibles = resultadosVisibles;
    }

    public String getCuadroInformacion() {
        return cuadroInformacion;
    }

    public void setCuadroInformacion(String cuadroInformacion) {
        this.cuadroInformacion = cuadroInformacion;
    }

    public boolean isVisibleCargaArchivos() {
        return visibleCargaArchivos;
    }

    public void setVisibleCargaArchivos(boolean visibleCargaArchivos) {
        this.visibleCargaArchivos = visibleCargaArchivos;
    }

    public Archivos getNuevoArchivo() {
        return nuevoArchivo;
    }

    public void setNuevoArchivo(Archivos nuevoArchivo) {
        this.nuevoArchivo = nuevoArchivo;
    }

    public List<LogDatos> getLogDatos() {
        return logDatos;
    }

    public void setLogDatos(List<LogDatos> logDatos) {
        this.logDatos = logDatos;
    }

    public List<String> getTiposArchivo() {
        return tiposArchivo;
    }

    public void setTiposArchivo(List<String> tiposArchivo) {
        this.tiposArchivo = tiposArchivo;
    }

    public String getTipobuses() {
        return tipobuses;
    }

    public void setTipobuses(String tipobuses) {
        this.tipobuses = tipobuses;
    }

    public UploadedFile getFileBuses() {
        return fileBuses;
    }

    public void setFileBuses(UploadedFile fileBuses) {
        this.fileBuses = fileBuses;
    }

    public Archivos getSelectedArchivo() {
        return selectedArchivo;
    }

    public void setSelectedArchivo(Archivos selectedArchivo) {
        this.selectedArchivo = selectedArchivo;
    }
}
