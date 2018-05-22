package com.datawarehouse.model.dao;


import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.Indicadores;
import com.datawarehouse.model.entity.Programacion;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.engine.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Transactional
public class IndicadoresDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addIndicador(Indicadores indicadores) {
        Serializable save = getSessionFactory().getCurrentSession().save(indicadores);

    }

    public void deleteIndicador(Indicadores indicadores) {
        getSessionFactory().getCurrentSession().delete(indicadores);
    }


    public void updateIndicador(Indicadores indicadores) {
        getSessionFactory().getCurrentSession().update(indicadores);
    }

    public Integer getNumeroBuses(Cuadro nuevoCuadro) {
        String sqlQuery = "Select CAST (r.bus AS INTEGER) as nbus " +
                "from dh_expediciones e " +
                "INNER JOIN dh_bus_registro r ON e.bus_registro = r.id " +
                "WHERE r.cuadro ='" +nuevoCuadro.getId()+"' "+
                "ORDER BY nbus DESC Limit 1";
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        SessionImplementor sessImpl = (SessionImplementor) session;
        Connection conn = sessImpl.getJDBCContext().connection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sqlQuery);
            ResultSet query_set = ps.executeQuery();
            query_set.next();
            return query_set.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public Double getKilometros(Cuadro nuevoCuadro,String tipo) {
        String sqlQuery = "Select SUM(e.kilometros) " +
                "from dh_expediciones e " +
                "INNER JOIN dh_bus_registro r ON e.bus_registro = r.id " +
                "WHERE r.cuadro ='"+nuevoCuadro.getId()+"' AND e.tipo='"+tipo+"'";
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        SessionImplementor sessImpl = (SessionImplementor) session;
        Connection conn = sessImpl.getJDBCContext().connection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sqlQuery);
            ResultSet query_set = ps.executeQuery();
            query_set.next();
            double valor= query_set.getDouble(1);
            valor = Math.floor(valor * 100) / 100;
            return valor;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }


}
