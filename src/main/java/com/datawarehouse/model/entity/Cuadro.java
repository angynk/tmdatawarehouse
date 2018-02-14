package com.datawarehouse.model.entity;

import javax.persistence.*;
import java.util.Date;

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

    public Cuadro() {
    }

    public String getNumero() {
        return numero;
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
}
