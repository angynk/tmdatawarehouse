package com.datawarehouse.view;



import com.datawarehouse.model.entity.Usuario;
import com.datawarehouse.model.servicios.UsuarioServicios;
import com.datawarehouse.view.util.Util;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String uname;
    private String nombreUsuario;
    private String password;
    private String role;
    private String logout;


    @ManagedProperty(value="#{navigationBean}")
    private NavigationBean navigationBean;

    @ManagedProperty(value="#{UsuariosService}")
    private UsuarioServicios usuarioServicios;

    @PostConstruct
    public void init(){
        System.out.println("");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String loginUser() {
        boolean result = false;
        Usuario usuario = usuarioServicios.encontrarUsuarioByNombreUsuario(uname);
        if (usuario != null) {
            if (usuario.getContrasena().equals(Util.md5(password)) && usuario.getRole().isPermisoEliminar()
                    && usuario.getRole().isPermisoEscribir() && usuario.getRole().isPermisoLeer()) {
                    HttpSession session = Util.getSession();
                    session.setAttribute("user", uname);
                    session.setAttribute("role", usuario.getRole());
                    this.role ="ADMIN";
                    this.nombreUsuario = usuario.getNombre();
                    return navigationBean.redirectToWelcome();
            } else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Inicio de sesion invalido",
                                "Verifique el usuario y la contraseña"));

            }
        }else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "El usuario no existe",
                            "Verifique el usuario y la contraseña"));

        }
        return navigationBean.toLogin();
    }

    public String logout() {
        HttpSession session = Util.getSession();
        session.invalidate();
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        try {
//            ec.redirect(ec.getRequestContextPath()
//                    + "/index.xhtml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return navigationBean.toLogin();
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }

    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    public UsuarioServicios getUsuarioServicios() {
        return usuarioServicios;
    }

    public void setUsuarioServicios(UsuarioServicios usuarioServicios) {
        this.usuarioServicios = usuarioServicios;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean hasRole(String role) {
        return this.role.equals(role);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
