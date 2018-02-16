package com.datawarehouse.model.dao;

import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.Programacion;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
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
}
