package com.datawarehouse.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="dh_bus")
public class Bus {

    @Id
    @Column(name = "numero")
    private Integer numero;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bus")
    private Set<BusRegistro> busesLista = new HashSet<BusRegistro>(0);


    public Bus() {
    }

    public Bus(Integer numero, Date fechaCreacion) {
        this.numero = numero;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Set<BusRegistro> getBusesLista() {
        return busesLista;
    }

    public void setBusesLista(Set<BusRegistro> busesLista) {
        this.busesLista = busesLista;
    }
}
