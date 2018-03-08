package com.datawarehouse.model.dao;

import com.datawarehouse.model.entity.Bus;
import com.datawarehouse.model.entity.BusRegistro;
import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.Programacion;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Repository
@Transactional
public class BusRegistroDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addBusRegistro(BusRegistro busRegistro) {
        Serializable save = getSessionFactory().getCurrentSession().save(busRegistro);

    }


    public BusRegistro encontrarBusRegistro(Bus bus,Programacion programacion) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(BusRegistro.class);
        criteria.add(Restrictions.eq("bus",bus));
        criteria.add(Restrictions.eq("programacion",programacion));
        return (BusRegistro) criteria.uniqueResult();
    }

    public BusRegistro encontrarBusRegistro(Bus bus,Cuadro cuadro) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(BusRegistro.class);
        criteria.add(Restrictions.eq("bus",bus));
        criteria.add(Restrictions.eq("cuadro",cuadro));
        return (BusRegistro) criteria.uniqueResult();
    }
}
