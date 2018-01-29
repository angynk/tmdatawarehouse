package com.datawarehouse.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean(name="menu")
@SessionScoped
public class MenuBean {

    public void refreshBVistaUsuarios(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/configUsuarios.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshBVistaRoles(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/configRoles.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshListadoTareas(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/configTareas.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
