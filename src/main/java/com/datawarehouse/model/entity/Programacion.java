package com.datawarehouse.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "modo")
    private String modo;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "programacion")
    private Set<BusRegistro> busesLista = new HashSet<BusRegistro>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "programacion")
    private Set<Archivos> archivosLista = new HashSet<Archivos>(0);

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

    public Set<BusRegistro> getBusesLista() {
        return busesLista;
    }

    public void setBusesLista(Set<BusRegistro> busesLista) {
        this.busesLista = busesLista;
    }

    public Set<Archivos> getArchivosLista() {
        return archivosLista;
    }

    public void setArchivosLista(Set<Archivos> archivosLista) {
        this.archivosLista = archivosLista;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
