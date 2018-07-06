package com.datawarehouse.model.dao;

import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.FechasProg;
import com.datawarehouse.model.entity.Programacion;
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
public class FechasProgDao {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addFechaProg(FechasProg fechasProg) {
        Serializable save = getSessionFactory().getCurrentSession().save(fechasProg);
    }

    public FechasProg existeFecha(Date fecha) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(FechasProg.class);
        criteria.add(Restrictions.eq("fecha",fecha));
        return (FechasProg) criteria.uniqueResult();
    }

    public List<FechasProg> getfechasByModo(Date date, String modo) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(FechasProg.class);
        criteria.add(Restrictions.eq("fecha",date));
        criteria.add(Restrictions.eq("modo",modo));
        return criteria.list();
    }

    public void eliminarFechasProgramacion(Programacion programacionSelected) {
        getSessionFactory().getCurrentSession().createSQLQuery("DELETE FROM dh_fechas_programacion WHERE programacion = "+programacionSelected.getId()).executeUpdate();
    }
}
