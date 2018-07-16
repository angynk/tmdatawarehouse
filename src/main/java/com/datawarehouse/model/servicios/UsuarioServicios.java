package com.datawarehouse.model.servicios;

import com.datawarehouse.model.dao.AplicacionDao;
import com.datawarehouse.model.dao.RolAplicacionDao;
import com.datawarehouse.model.dao.UsuarioDao;
import com.datawarehouse.model.entity.Aplicacion;
import com.datawarehouse.model.entity.RolAplicacion;
import com.datawarehouse.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UsuariosService")
public class UsuarioServicios {

    @Autowired
    public UsuarioDao usuarioDao;

    @Autowired
    public AplicacionDao aplicacionDao;

    @Autowired
    public RolAplicacionDao rolAplicacionDao;

    public void addUsuario(Usuario usuario) {
        usuarioDao.addUsuario(usuario);
    }

    public void deleteUsuario(Usuario usuario) {
       usuarioDao.deleteUsuario(usuario);
    }


    public void updateUsuario(Usuario usuario) {
        usuarioDao.updateUsuario(usuario);
    }


    public List<Usuario> getUsuarioAll() {
        return usuarioDao.getUsuarioAll();
    }

    public Usuario encontrarUsuarioByNombreUsuario(String user){
        return usuarioDao.encontrarUsuarioByNombreUsuario(user);
    }

    public Aplicacion getAplicacion(int idAplicacion) {
        return aplicacionDao.obtenerAplicacionById(idAplicacion);
    }

    public RolAplicacion getRolUsuarioAplicacion(Aplicacion aplicacion, Usuario usuario) {
        return rolAplicacionDao.getRolAplicacion(aplicacion,usuario);
    }
}
