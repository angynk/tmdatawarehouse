package com.datawarehouse.view;

import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.model.servicios.DuplicarProgramacionProcessor;
import com.datawarehouse.view.util.Util;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "duplicarBean")
@ViewScoped
public class DuplicarIndicadoresBean {

    private boolean visibleDuplicacion;
    private String tituloPanel;
    private String fechas;
    private String fechaSelected;
    private String modo;
    private List<String> modos;
    private Date fechaADuplicar;
    private List<String> logDatos;
    private boolean resultadosVisibles;
    private String razonProgramacion;
    private String nuevaDistribucion;
    private boolean cargaNuevoArchivoVisible;
    private Date fechaInicio;
    private Date fechaFin;
    private UploadedFile file;
    private List<String> formatosArchivo;
    private String tipo;


    private Programacion selectedProg;
    private List<String> progDateList;


    @ManagedProperty("#{DuplicarProcessor}")
    private DuplicarProgramacionProcessor duplicarProgramacionProcessor;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public DuplicarIndicadoresBean() {
    }

    @PostConstruct
    public void init() {
        resultadosVisibles = false;
        modo = "TRO";
        modos =  Util.listaModos();
        visibleDuplicacion = false;
        cargaNuevoArchivoVisible = false;
        nuevaDistribucion = "1";
        formatosArchivo = Util.listaFormatosCSV();
    }

    public void cambioArchivoDistribuciones(){
        if(nuevaDistribucion.equals("2")){
            cargaNuevoArchivoVisible = true;
        }else {
            cargaNuevoArchivoVisible = false;
        }
    }

    public void duplicar(){
        //Obtener fechas
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        fechas = ec.getRequestParameterMap().get("fechas");
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            fechaADuplicar = format.parse(fechaSelected);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(duplicarProgramacionProcessor.existeProgramacionParaLaFecha(fechas,modo)){
            duplicarDatos();
        }else{
            messagesView.info("Error","Ya existe una programación asociada a la fecha");
        }


    }

    public void duplicarDatos(){
        logDatos = duplicarProgramacionProcessor.duplicarProgramacion(fechaADuplicar,fechas,modo,razonProgramacion);
        resultadosVisibles = true;
//        if(duplicarProgramacionProcessor.isDuplicacionValida()){
//            messagesView.info("Carga existosa","");
//        }else{
////            messagesView.error(Messages.MENSAJE_CARGA_FALLIDA,Messages.VALIDE_LOG);
//        }
    }

    public void continuarDuplicacion(){
            duplicarDatos();
    }

    public void finalizarDuplicacion(){
//        messagesView.error(Messages.MENSAJE_CARGA_FALLIDA,"La programación no fue duplicada");
    }

    public void updateProgList(){
        if(fechaInicio != null && fechaFin != null ){
            List<Programacion> allProgramacionbyModo = duplicarProgramacionProcessor.getAllProgramacionbyModo(Util.convertirModo(modo),fechaInicio,fechaFin);
            progDateList = convertirFechasLista(allProgramacionbyModo);
            if(progDateList.size()>0){
                visibleDuplicacion = true;
                tituloPanel = "Duplicar Programación -  "+modo;
            }else{
                messagesView.error("Error","No existe niguna programación para las fechas y el modo seleccionado");
            }

        }else{
            messagesView.error("Error","Seleccione las fechas de busqueda");
        }

    }

    private List<String> convertirFechasLista(List<Programacion> allProgramacionbyModo) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        List<String> fechas = new ArrayList<>();
        for(Programacion programacion:allProgramacionbyModo){
            if(!fechas.contains(sdfDate.format(programacion.getFecha()))){

                fechas.add(sdfDate.format(programacion.getFecha()));
            }

        }
        return fechas;
    }

//    public Programacion getProgramacionbyID(long id){
//        return duplicarProgramacionProcessor.getProgramacionbyID(id);
////    }

    public String getRazonProgramacion() {
        return razonProgramacion;
    }

    public void setRazonProgramacion(String razonProgramacion) {
        this.razonProgramacion = razonProgramacion;
    }

    public String getFechas() {
        return fechas;
    }

    public void setFechas(String fechas) {
        this.fechas = fechas;
    }

    public Date getFechaADuplicar() {
        return fechaADuplicar;
    }

    public void setFechaADuplicar(Date fechaADuplicar) {
        this.fechaADuplicar = fechaADuplicar;
    }


    public List<String> getLogDatos() {
        return logDatos;
    }

    public void setLogDatos(List<String> logDatos) {
        this.logDatos = logDatos;
    }

    public boolean isResultadosVisibles() {
        return resultadosVisibles;
    }

    public void setResultadosVisibles(boolean resultadosVisibles) {
        this.resultadosVisibles = resultadosVisibles;
    }

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }




    public Programacion getSelectedProg() {
        return selectedProg;
    }

    public void setSelectedProg(Programacion selectedProg) {
        this.selectedProg = selectedProg;
    }

    public List<String> getModos() {
        return modos;
    }

    public void setModos(List<String> modos) {
        this.modos = modos;
    }

    public String getFechaSelected() {
        return fechaSelected;
    }

    public void setFechaSelected(String fechaSelected) {
        this.fechaSelected = fechaSelected;
    }

    public boolean isVisibleDuplicacion() {
        return visibleDuplicacion;
    }

    public void setVisibleDuplicacion(boolean visibleDuplicacion) {
        this.visibleDuplicacion = visibleDuplicacion;
    }

    public List<String> getProgDateList() {
        return progDateList;
    }

    public void setProgDateList(List<String> progDateList) {
        this.progDateList = progDateList;
    }

    public String getTituloPanel() {
        return tituloPanel;
    }

    public void setTituloPanel(String tituloPanel) {
        this.tituloPanel = tituloPanel;
    }


    public DuplicarProgramacionProcessor getDuplicarProgramacionProcessor() {
        return duplicarProgramacionProcessor;
    }

    public void setDuplicarProgramacionProcessor(DuplicarProgramacionProcessor duplicarProgramacionProcessor) {
        this.duplicarProgramacionProcessor = duplicarProgramacionProcessor;
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

    public String getNuevaDistribucion() {
        return nuevaDistribucion;
    }

    public void setNuevaDistribucion(String nuevaDistribucion) {
        this.nuevaDistribucion = nuevaDistribucion;
    }

    public boolean isCargaNuevoArchivoVisible() {
        return cargaNuevoArchivoVisible;
    }

    public void setCargaNuevoArchivoVisible(boolean cargaNuevoArchivoVisible) {
        this.cargaNuevoArchivoVisible = cargaNuevoArchivoVisible;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
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
