package com.datawarehouse.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean(name="menu")
@SessionScoped
public class MenuBean {

    public void refreshCargarDatos(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/crearProgramacion.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshDuplicarProgramacion(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/duplicarProgramacion.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void refreshBuscarProgramacion(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/buscarProgramacion.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshConsultasBasicas(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/consultasBasicas.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshConsultasAvanzadas(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/consultasAvanzadas.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshActualizarDatos(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/crearCuadro.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshActualizarCuadro(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/cargarDatos.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshConsultarDatos(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/configTareas.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
