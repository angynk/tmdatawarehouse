package com.datawarehouse.model.dao;

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
import java.sql.Connection;
import java.sql.SQLException;

@Repository
@Transactional
public class TracelogDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void cargarArchivoTracelog(String filename) throws Exception {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        SessionImplementor sessImpl = (SessionImplementor) session;
        Connection conn = null;
        conn = sessImpl.getJDBCContext().connection();
        CopyManager copyManager = null;
        try {
            copyManager = new CopyManager((BaseConnection) conn);
            FileReader fileReader = new FileReader(filename);
            copyManager.copyIn("COPY dh_tracelog (tipo_solucion, numero_solucion, tiempo_solucion, subfase, iteracion, num_buses, km_linea, km_vacio, num_viajes, num_viajes_vacio,num_cambio_linea, total_horas, horas_vacio, coste_total_buses, coste_hora_desvio, coste_cambios_linea, plus_cambios_linea, plus_autobus,plus_km_vacio, plus_km, plus_exp_vacio, plus_expediciones,jornada)" +
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
        getSessionFactory().getCurrentSession().createSQLQuery("DELETE FROM dh_tracelog WHERE jornada = '"+nuevaProgramacion.getIdentificador()+"'").executeUpdate();
    }


}

