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

    @Column(name = "vacio_interno")
    private Integer vacioInterno;

    @Column(name = "vacio_valle")
    private Integer vacioValle;

    @Column(name = "vacio_externo")
    private Integer vacioExterno;

    @Column(name = "vacio_total")
    private Integer vacioTotal;

    @Column(name = "distancia_comercial")
    private Integer distanciaComercial;


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

    public Integer getVacioInterno() {
        return vacioInterno;
    }

    public void setVacioInterno(Integer vacioInterno) {
        this.vacioInterno = vacioInterno;
    }

    public Integer getVacioValle() {
        return vacioValle;
    }

    public void setVacioValle(Integer vacioValle) {
        this.vacioValle = vacioValle;
    }

    public Integer getVacioExterno() {
        return vacioExterno;
    }

    public void setVacioExterno(Integer vacioExterno) {
        this.vacioExterno = vacioExterno;
    }

    public Integer getVacioTotal() {
        return vacioTotal;
    }

    public void setVacioTotal(Integer vacioTotal) {
        this.vacioTotal = vacioTotal;
    }

    public Integer getDistanciaComercial() {
        return distanciaComercial;
    }

    public void setDistanciaComercial(Integer distanciaComercial) {
        this.distanciaComercial = distanciaComercial;
    }
}
