package com.datawarehouse.model.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name="dh_tabla_horario")
public class TablaHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="TablaHorarioGenerator")
    @SequenceGenerator(name="TablaHorarioGenerator", sequenceName = "dh_tabla_horario_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "jornada_tipo")
    private String jornadaTipo;

    @Column(name = "tipo_dia")
    private String tipoDia;

    @Column(name = "operador")
    private Integer operador;

    @Column(name = "instante")
    private Time instante;

    @Column(name = "evento")
    private Integer evento;

    @Column(name = "linea")
    private Integer linea;

    @Column(name = "coche")
    private Integer coche;

    @Column(name = "sublinea")
    private Integer sublinea;

    @Column(name = "ruta")
    private Integer ruta;

    @Column(name = "punto")
    private Integer punto;

    @Column(name = "tipo_nodo")
    private Integer tipoNodo;

    @Column(name = "viaje")
    private Integer viaje;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bus_registro", nullable = false)
    private BusRegistro busRegistro;

    public TablaHorario() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJornadaTipo() {
        return jornadaTipo;
    }

    public void setJornadaTipo(String jornadaTipo) {
        this.jornadaTipo = jornadaTipo;
    }

    public String getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia) {
        this.tipoDia = tipoDia;
    }

    public Integer getOperador() {
        return operador;
    }

    public void setOperador(Integer operador) {
        this.operador = operador;
    }

    public Time getInstante() {
        return instante;
    }

    public void setInstante(Time instante) {
        this.instante = instante;
    }

    public Integer getEvento() {
        return evento;
    }

    public void setEvento(Integer evento) {
        this.evento = evento;
    }

    public Integer getLinea() {
        return linea;
    }

    public void setLinea(Integer linea) {
        this.linea = linea;
    }

    public Integer getCoche() {
        return coche;
    }

    public void setCoche(Integer coche) {
        this.coche = coche;
    }

    public Integer getSublinea() {
        return sublinea;
    }

    public void setSublinea(Integer sublinea) {
        this.sublinea = sublinea;
    }

    public Integer getRuta() {
        return ruta;
    }

    public void setRuta(Integer ruta) {
        this.ruta = ruta;
    }

    public Integer getPunto() {
        return punto;
    }

    public void setPunto(Integer punto) {
        this.punto = punto;
    }

    public Integer getTipoNodo() {
        return tipoNodo;
    }

    public void setTipoNodo(Integer tipoNodo) {
        this.tipoNodo = tipoNodo;
    }

    public Integer getViaje() {
        return viaje;
    }

    public void setViaje(Integer viaje) {
        this.viaje = viaje;
    }

    public BusRegistro getBusRegistro() {
        return busRegistro;
    }

    public void setBusRegistro(BusRegistro busRegistro) {
        this.busRegistro = busRegistro;
    }
}
