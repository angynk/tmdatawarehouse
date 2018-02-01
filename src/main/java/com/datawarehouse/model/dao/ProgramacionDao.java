package com.datawarehouse.model.dao;

import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.model.entity.Role;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ProgramacionDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addProgramacion(Programacion programacion) {
        Serializable save = getSessionFactory().getCurrentSession().save(programacion);

    }

    public void deleteProgramacion(Programacion programacion) {
        getSessionFactory().getCurrentSession().delete(programacion);
    }


    public void updateProgramacion(Programacion programacion) {
        getSessionFactory().getCurrentSession().update(programacion);
    }

    public Programacion encontrarProgramacion(String identificador) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Programacion.class);
        criteria.add(Restrictions.eq("identificador",identificador));
        return (Programacion) criteria.uniqueResult();
    }

    public List<Programacion> getProgramaciones(Date fechaInicio, Date fechaFin, String tipoDia) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Programacion.class);
        criteria.add(Restrictions.eq("tipoDia",tipoDia));
        criteria.add(Restrictions.between("fecha",fechaInicio,fechaFin));
        return criteria.list();
    }
}
