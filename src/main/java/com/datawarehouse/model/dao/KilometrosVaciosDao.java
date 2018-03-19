package com.datawarehouse.model.dao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.engine.SessionImplementor;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

@Repository
@Transactional
public class KilometrosVaciosDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void generarReporteTotalVaciosOperador(String archivoReporte, Date fechaInicio, Date fechaFin) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        SessionImplementor sessImpl = (SessionImplementor) session;
        Connection conn = null;
        conn = sessImpl.getJDBCContext().connection();
        CopyManager copyManager = null;
        BufferedWriter fileWriter = null;
        try {
            fileWriter = new BufferedWriter(new FileWriter(archivoReporte));
            String sqlQuery = "SELECT SUM(d.vacio_total) as vacio, d.operador as operador" +
                              "FROM dh_distribucion d " +
                              "INNER JOIN dh_bus_registro b " +
                              "ON d.bus_registro = b.id " +
                              "INNER JOIN dh_cuadro c " +
                              "ON b.cuadro = c.id " +
                              "INNER JOIN dh_programacion p " +
                              "ON c.programacion = p.id " +
                              "WHERE p.fecha BETWEEN '"+fechaInicio+"' AND '"+fechaFin+"'"+
                              " GROUP BY (d.operador)";


            copyManager = new CopyManager((BaseConnection) conn);
            copyManager.copyOut("COPY ("+sqlQuery+") TO STDOUT WITH DELIMITER ';'",fileWriter);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generarReporteDiaADiaVaciosOperador(String archivoReporte, Date fechaInicio, Date fechaFin) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        SessionImplementor sessImpl = (SessionImplementor) session;
        Connection conn = null;
        conn = sessImpl.getJDBCContext().connection();
        CopyManager copyManager = null;
        BufferedWriter fileWriter = null;
        try {
            fileWriter = new BufferedWriter(new FileWriter(archivoReporte));
            String sqlQuery = "SELECT f.fecha ,SUM(d.vacio_total), d.operador " +
                    "FROM dh_distribucion d " +
                    "INNER JOIN dh_bus_registro b " +
                    "ON d.bus_registro = b.id " +
                    "INNER JOIN dh_cuadro c " +
                    "ON b.cuadro = c.id " +
                    "INNER JOIN dh_programacion p " +
                    "ON c.programacion = p.id " +
                    "INNER JOIN dh_fechas_programacion f " +
                    "ON p.id = f.programacion " +
                    "WHERE f.fecha BETWEEN '"+fechaInicio+"' AND '"+fechaFin+"'" +
                    "GROUP BY d.operador,f.fecha " +
                    "ORDER BY f.fecha";


            copyManager = new CopyManager((BaseConnection) conn);
            copyManager.copyOut("COPY ("+sqlQuery+") TO STDOUT WITH DELIMITER ';'",fileWriter);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
