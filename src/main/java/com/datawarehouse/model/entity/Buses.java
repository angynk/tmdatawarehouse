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

}
