package com.datawarehouse.model.dao;

import com.datawarehouse.model.entity.Archivos;
import com.datawarehouse.model.entity.Bus;
import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.Programacion;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class ArchivosDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addArchivos(Archivos archivos) {
        archivos.setAdjuntado(true);
        Serializable save = getSessionFactory().getCurrentSession().save(archivos);
    }

    public List<Archivos> encontrarArchivos(Cuadro cuadro) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Archivos.class);
        criteria.add(Restrictions.eq("cuadro",cuadro));
        return criteria.list();
    }

    public void deleteArchivo(Archivos selectedArchivo) {
        getSessionFactory().getCurrentSession().delete(selectedArchivo);
    }

    public void eliminarArchivosCuadro(Cuadro cuadro) {
        getSessionFactory().getCurrentSession().createSQLQuery("DELETE FROM dh_archivo WHERE cuadro = "+cuadro.getId()).executeUpdate();
    }
}
