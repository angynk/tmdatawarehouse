package com.datawarehouse.model.dao;

import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.Programacion;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionImplementor;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class CuadroDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addCuadro(Cuadro cuadro) {
        Serializable save = getSessionFactory().getCurrentSession().save(cuadro);
    }

    public List<Cuadro> obtenerCuadrosProgramacion(Programacion progr) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Cuadro.class);
        criteria.add(Restrictions.eq("programacion",progr));
        return criteria.list();
    }

    public Cuadro obtenerCuadro(Programacion programacion, String cuadro) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Cuadro.class);
        criteria.add(Restrictions.eq("programacion",programacion));
        criteria.add(Restrictions.eq("numero",cuadro));
        return (Cuadro) criteria.uniqueResult();
    }

    public String duplicarDatosCuadro(Cuadro cuadroViejo, Cuadro cuadroNuevo, java.sql.Date fecha) {
        String SQL = "SELECT duplicar_cuadro_programacion(?,?,?)";
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        SessionImplementor sessImpl = (SessionImplementor) session;
        Connection conn = null;
        conn = sessImpl.getJDBCContext().connection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);

                pstmt.setDate(1, fecha);
                pstmt.setInt(2, (int) cuadroViejo.getId());
                pstmt.setInt(3, (int) cuadroNuevo.getId());
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                   return rs.getString(1);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Duplicación fallo";
    }

    public List<Cuadro> obtenerCuadroNumero(String numero) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Cuadro.class);
        criteria.add(Restrictions.eq("numero",numero));
        return criteria.list();
    }
}
