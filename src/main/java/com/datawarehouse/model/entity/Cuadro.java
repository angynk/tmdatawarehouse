package com.datawarehouse.model.entity;

import com.datawarehouse.view.util.Util;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="dh_cuadro")
public class Cuadro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="CuadroGenerator")
    @SequenceGenerator(name="CuadroGenerator", sequenceName = "dh_cuadro_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "programacion", nullable = false)
    private Programacion programacion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cuadro")
    private Set<Archivos> archivosLista = new HashSet<Archivos>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cuadro")
    private Set<BusRegistro> busesLista = new HashSet<BusRegistro>(0);

    public Cuadro() {
    }

    public String getNumero() {
        return numero;
    }

    public String getIdentificador(){
        return numero+"/"+ Util.convertirAString(fecha);
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Programacion getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Programacion programacion) {
        this.programacion = programacion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Archivos> getArchivosLista() {
        return archivosLista;
    }

    public void setArchivosLista(Set<Archivos> archivosLista) {
        this.archivosLista = archivosLista;
    }

    public Set<BusRegistro> getBusesLista() {
        return busesLista;
    }

    public void setBusesLista(Set<BusRegistro> busesLista) {
        this.busesLista = busesLista;
    }
}
