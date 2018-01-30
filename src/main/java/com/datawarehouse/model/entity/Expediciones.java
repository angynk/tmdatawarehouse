package com.datawarehouse.model.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name="dh_expediciones")
public class Expediciones {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="ExpedicionesGenerator")
    @SequenceGenerator(name="ExpedicionesGenerator", sequenceName = "dh_expediciones_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "evento")
    private String evento;

    @Column(name = "inicio")
    private Time inicio;

    @Column(name = "fin")
    private Time fin;

    @Column(name = "duracion")
    private Time duracion;

    @Column(name = "punto_inicio")
    private Integer puntoInicio;

    @Column(name = "punto_fin")
    private Integer puntoFin;

    @Column(name = "kilometros")
    private Double kilometros;

    @Column(name = "linea")
    private String linea;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bus_registro", nullable = false)
    private BusRegistro busRegistro;

    public Expediciones() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
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

    public Time getDuracion() {
        return duracion;
    }

    public void setDuracion(Time duracion) {
        this.duracion = duracion;
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

    public Double getKilometros() {
        return kilometros;
    }

    public void setKilometros(Double kilometros) {
        this.kilometros = kilometros;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public BusRegistro getBusRegistro() {
        return busRegistro;
    }

    public void setBusRegistro(BusRegistro busRegistro) {
        this.busRegistro = busRegistro;
    }
}
