package com.datawarehouse.model.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name="dh_buses")
public class Buses {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="BusesGenerator")
    @SequenceGenerator(name="BusesGenerator", sequenceName = "dh_buses_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "inicio")
    private Time inicio;

    @Column(name = "fin")
    private Time fin;

    @Column(name = "jornada")
    private Time jornada;

    @Column(name = "circ")
    private Time circ;

    @Column(name = "punto_inicio")
    private Integer puntoInicio;

    @Column(name = "punto_fin")
    private Integer puntoFin;

    @Column(name = "linea_inicio")
    private String lineaInicio;

    @Column(name = "linea_fin")
    private String lineaFin;

    @Column(name = "kilometros")
    private Double kilometros;

    @Column(name = "tipologia")
    private String tipologia;

    public Buses() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Time getInicio() {
        return inicio;
    }

    public void setInicio(Time inicio) {
        this.inicio = inicio;
    }

    public Time getFin() {
        return fin;
    }

    public void setFin(Time fin) {
        this.fin = fin;
    }

    public Time getJornada() {
        return jornada;
    }

    public void setJornada(Time jornada) {
        this.jornada = jornada;
    }

    public Time getCirc() {
        return circ;
    }

    public void setCirc(Time circ) {
        this.circ = circ;
    }

    public Integer getPuntoInicio() {
        return puntoInicio;
    }

    public void setPuntoInicio(Integer puntoInicio) {
        this.puntoInicio = puntoInicio;
    }

    public Integer getPuntoFin() {
        return puntoFin;
    }

    public void setPuntoFin(Integer puntoFin) {
        this.puntoFin = puntoFin;
    }

    public String getLineaInicio() {
        return lineaInicio;
    }

    public void setLineaInicio(String lineaInicio) {
        this.lineaInicio = lineaInicio;
    }

    public String getLineaFin() {
        return lineaFin;
    }

    public void setLineaFin(String lineaFin) {
        this.lineaFin = lineaFin;
    }

    public Double getKilometros() {
        return kilometros;
    }

    public void setKilometros(Double kilometros) {
        this.kilometros = kilometros;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }
}
