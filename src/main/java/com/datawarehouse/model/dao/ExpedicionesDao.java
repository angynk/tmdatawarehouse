package com.datawarehouse.model.dao;

import com.datawarehouse.model.entity.Expediciones;
import com.datawarehouse.model.entity.Operador;
import com.datawarehouse.model.entity.Programacion;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
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
import java.sql.SQLException;

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

    public void cargarArchivoExpediciones(String filename) throws Exception {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        SessionImplementor sessImpl = (SessionImplementor) session;
        Connection conn = null;
        conn = sessImpl.getJDBCContext().connection();
        CopyManager copyManager = null;
        try {
            copyManager = new CopyManager((BaseConnection) conn);
            FileReader fileReader = new FileReader(filename);
            copyManager.copyIn("COPY dh_temp_exp (evento,tipo,inicio,punto_inicio,fin,punto_fin,duracion,bus,linea,kilometros,inferido,identi,frec,serbus,des_dur,desc_frec,jornada,modo)" +
                    " from  STDIN DELIMITER ',' CSV HEADER encoding 'windows-1251' ", fileReader );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public void eliminarDatos(Programacion nuevaProgramacion) {
        getSessionFactory().getCurrentSession().createSQLQuery("DELETE FROM dh_temp_exp WHERE jornada = '"+nuevaProgramacion.getIdentificador()+"'").executeUpdate();
    }
}
