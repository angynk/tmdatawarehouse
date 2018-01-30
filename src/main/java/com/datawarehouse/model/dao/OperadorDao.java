package com.datawarehouse.model.dao;

import com.datawarehouse.model.entity.Bus;
import com.datawarehouse.model.entity.BusRegistro;
import com.datawarehouse.model.entity.Operador;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Repository
@Transactional
public class OperadorDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addBusOperador(Operador operador) {
        Serializable save = getSessionFactory().getCurrentSession().save(operador);

    }

    public Operador encontrarOperadorDefault() {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Operador.class);
        criteria.add(Restrictions.eq("codigo",0));
        return (Operador) criteria.uniqueResult();
    }
}
