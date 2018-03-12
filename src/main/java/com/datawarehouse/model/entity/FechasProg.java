package com.datawarehouse.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="dh_fechas_programacion")
public class FechasProg {

    @Id
    @Column(name = "fecha")
    private Date fecha;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "programacion", nullable = false)
    private Programacion programacion;

    public FechasProg() {
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Programacion getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Programacion programacion) {
        this.programacion = programacion;
    }
}
