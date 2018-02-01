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

    public void refreshActualizarDatos(){
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
