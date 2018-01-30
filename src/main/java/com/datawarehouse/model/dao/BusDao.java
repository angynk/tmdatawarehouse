package com.datawarehouse.model.dao;

import com.datawarehouse.model.entity.Bus;
import com.datawarehouse.model.entity.BusRegistro;
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
public class BusDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addBus(Bus bus) {
        Serializable save = getSessionFactory().getCurrentSession().save(bus);

    }

    public Bus encontrarBus(Integer numeroBus) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Bus.class);
        criteria.add(Restrictions.eq("numero",numeroBus));
        return (Bus) criteria.uniqueResult();
    }
}
