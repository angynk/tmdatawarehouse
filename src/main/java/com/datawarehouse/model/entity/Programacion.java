package com.datawarehouse.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="dh_programacion")
public class Programacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="ProgramacionGenerator")
    @SequenceGenerator(name="ProgramacionGenerator", sequenceName = "dh_programacion_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "jornada")
    private String jornada;

    @Column(name = "identificador")
    private String identificador;

    @Column(name = "tipo_dia")
    private String tipoDia;

    public Programacion(Date fecha, String jornada, String identificador) {
        this.fecha = fecha;
        this.jornada = jornada;
        this.identificador = identificador;
    }

    public Programacion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia) {
        this.tipoDia = tipoDia;
    }
}
