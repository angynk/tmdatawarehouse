package com.datawarehouse.view;



import com.datawarehouse.model.entity.Aplicacion;
import com.datawarehouse.model.entity.RolAplicacion;
import com.datawarehouse.model.entity.Role;
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
    private String area;
    private Role role;
    private String logout;
    private Usuario usuario;

    //Perfil Usuario
    public boolean cambioContrasena ;
    public String contrasenaAntigua;
    public String contrasenaNueva;
    public String contrasenaNuevaRep;

    private static  int ID_APLICACION = 3;


    @ManagedProperty(value="#{navigationBean}")
    private NavigationBean navigationBean;

    @ManagedProperty(value="#{UsuariosService}")
    private UsuarioServicios usuarioServicios;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

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
                this.role =obtenerRol(usuario);
                if(role!=null){
                    this.nombreUsuario = usuario.getNombre();
                    this.area = usuario.getArea();
                    this.usuario = usuario;
                    return navigationBean.redirectToWelcome();
                }else{
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Inicio de sesion invalido",
                                    "Usted no tiene permisos sobre esta aplicación, contacte al administrador"));
                }
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
        return navigationBean.redirectToLogin();
    }

    public String gotoperfil(){
        return "/secured/miPerfil.xhtml?faces-redirect=true";
    }

    public boolean puedeEditar(){
        if(role!= null){
            return true;
        }
        return true;
    }

    public boolean puedeEliminar(){
        if(role!= null){
            return true;
        }
        return false;
    }

    private boolean contrasenasNuevasIguales() {
        if(contrasenaNueva.equals(contrasenaNuevaRep)) return true;
        return false;
    }

    private Role obtenerRol(Usuario usuario) {

        // Obtener aplicacion
        Aplicacion aplicacion = usuarioServicios.getAplicacion(ID_APLICACION);
        RolAplicacion rolAplicacion = usuarioServicios.getRolUsuarioAplicacion(aplicacion,usuario);
        if(rolAplicacion!=null){
            return rolAplicacion.getRole();
        }
        return null;
    }

    private boolean contrasenaViejaEsCorrecta() {
        contrasenaAntigua = Util.md5(contrasenaAntigua);
        if(contrasenaAntigua.equals(usuario.getContrasena())) return true;

        return false;
    }

    public void modificarContrasena(){
        cambioContrasena = true;
    }

    public void cambiar(){
        if(contrasenaViejaEsCorrecta()) {
            if(contrasenasNuevasIguales()){
                usuario.setContrasena(Util.md5(contrasenaNueva));
                usuarioServicios.updateUsuario(usuario);
                messagesView.info("Proceso Exitoso","La contraseña fue modificada");
            }else{
                messagesView.error("Proceso Fallido","Las contraseñas no coinciden");
            }
        }else{
            messagesView.error("Proceso Fallido","La contraseña vieja no coincide");
        }
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isCambioContrasena() {
        return cambioContrasena;
    }

    public void setCambioContrasena(boolean cambioContrasena) {
        this.cambioContrasena = cambioContrasena;
    }

    public String getContrasenaAntigua() {
        return contrasenaAntigua;
    }

    public void setContrasenaAntigua(String contrasenaAntigua) {
        this.contrasenaAntigua = contrasenaAntigua;
    }

    public String getContrasenaNueva() {
        return contrasenaNueva;
    }

    public void setContrasenaNueva(String contrasenaNueva) {
        this.contrasenaNueva = contrasenaNueva;
    }

    public String getContrasenaNuevaRep() {
        return contrasenaNuevaRep;
    }

    public void setContrasenaNuevaRep(String contrasenaNuevaRep) {
        this.contrasenaNuevaRep = contrasenaNuevaRep;
    }

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
