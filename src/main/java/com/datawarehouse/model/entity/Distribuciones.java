package com.datawarehouse.model.entity;

import javax.persistence.*;

@Entity
@Table(name="dh_distribucion")
public class Distribuciones {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="DistribucionesGenerator")
    @SequenceGenerator(name="DistribucionesGenerator", sequenceName = "dh_distribucion_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bus_registro", nullable = false)
    private BusRegistro busRegistro;

    @Column(name = "tipologia")
    private String tipologia;

    @Column(name = "serbus")
    private String serbus;

    @Column(name = "operador")
    private String operador;

    @Column(name = "patio_ini")
    private String patioIni;

    @Column(name = "patio_fin")
    private String patioFin;

    @Column(name = "patio_valle")
    private String patioValle;

    @Column(name = "punto_inicio")
    private String puntoInicio;

    @Column(name = "punto_fin")
    private String puntoFin;

    @Column(name = "distancia")
    private String distancia;

    @Column(name = "vacio_interno_sin_valle")
    private Double vacioInternoSinValle;



    public Distribuciones() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BusRegistro getBusRegistro() {
        return busRegistro;
    }

    public void setBusRegistro(BusRegistro busRegistro) {
        this.busRegistro = busRegistro;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getSerbus() {
        return serbus;
    }

    public void setSerbus(String serbus) {
        this.serbus = serbus;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getPatioIni() {
        return patioIni;
    }

    public void setPatioIni(String patioIni) {
        this.patioIni = patioIni;
    }

    public String getPatioFin() {
        return patioFin;
    }

    public void setPatioFin(String patioFin) {
        this.patioFin = patioFin;
    }

    public String getPatioValle() {
        return patioValle;
    }

    public void setPatioValle(String patioValle) {
        this.patioValle = patioValle;
    }

    public String getPuntoInicio() {
        return puntoInicio;
    }

    public void setPuntoInicio(String puntoInicio) {
        this.puntoInicio = puntoInicio;
    }

    public String getPuntoFin() {
        return puntoFin;
    }

    public void setPuntoFin(String puntoFin) {
        this.puntoFin = puntoFin;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public Double getVacioInternoSinValle() {
        return vacioInternoSinValle;
    }

    public void setVacioInternoSinValle(Double vacioInternoSinValle) {
        this.vacioInternoSinValle = vacioInternoSinValle;
    }
}
