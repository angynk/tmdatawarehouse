package com.datawarehouse.model.dao;

import com.datawarehouse.model.entity.Expediciones;
import com.datawarehouse.model.entity.Operador;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Repository
@Transactional
public class ExpedicionesDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addExpedicion(Expediciones expediciones) {
        Serializable save = getSessionFactory().getCurrentSession().save(expediciones);

    }
}
