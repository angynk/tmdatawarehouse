package com.datawarehouse.model.entity;

import javax.persistence.*;

@Entity
@Table(name="dh_tracelog")
public class Tracelog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="TracelogGenerator")
    @SequenceGenerator(name="TracelogGenerator", sequenceName = "dh_tracelog_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "tipo_solucion")
    private String tipoSolucion;

    @Column(name = "numero_solucion")
    private Integer numeroSolucion;

    @Column(name = "tiempo_solucion")
    private String tiempoSolucion;

    @Column(name = "subfase")
    private String subfase;

    @Column(name = "num_buses")
    private Integer num_buses;

    @Column(name = "km_linea")
    private String kmLinea;

    @Column(name = "km_vacio")
    private String kmVacio;

    @Column(name = "num_viajes")
    private Integer numViajes;

    @Column(name = "num_viajes_vacio")
    private Integer numViajesVacio;

    @Column(name = "num_cambio_linea")
    private String numCambioLinea;

    @Column(name = "total_horas")
    private String totalHoras;

    @Column(name = "horas_vacio")
    private String horasVacio;

    @Column(name = "coste_total_buses")
    private String costeTotalBuses;

    @Column(name = "coste_hora_desvio")
    private String costeHoraDesvio;

    @Column(name = "coste_cambios_linea")
    private String costeCambiosLinea;

    @Column(name = "plus_cambios_linea")
    private String plusCambiosLinea;

    @Column(name = "plus_autobus")
    private String plusAutobus;

    @Column(name = "plus_km_vacio")
    private String plusKmVacio;

    @Column(name = "plus_km")
    private String plusKilometros;

    @Column(name = "plus_exp_vacio")
    private String plusExpVacio;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_programacion", nullable = false)
    private Programacion programacion;

    public Tracelog() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipoSolucion() {
        return tipoSolucion;
    }

    public void setTipoSolucion(String tipoSolucion) {
        this.tipoSolucion = tipoSolucion;
    }

    public Integer getNumeroSolucion() {
        return numeroSolucion;
    }

    public void setNumeroSolucion(Integer numeroSolucion) {
        this.numeroSolucion = numeroSolucion;
    }

    public String getTiempoSolucion() {
        return tiempoSolucion;
    }

    public void setTiempoSolucion(String tiempoSolucion) {
        this.tiempoSolucion = tiempoSolucion;
    }

    public String getSubfase() {
        return subfase;
    }

    public void setSubfase(String subfase) {
        this.subfase = subfase;
    }

    public Integer getNum_buses() {
        return num_buses;
    }

    public void setNum_buses(Integer num_buses) {
        this.num_buses = num_buses;
    }

    public String getKmLinea() {
        return kmLinea;
    }

    public void setKmLinea(String kmLinea) {
        this.kmLinea = kmLinea;
    }

    public String getKmVacio() {
        return kmVacio;
    }

    public void setKmVacio(String kmVacio) {
        this.kmVacio = kmVacio;
    }

    public Integer getNumViajes() {
        return numViajes;
    }

    public void setNumViajes(Integer numViajes) {
        this.numViajes = numViajes;
    }

    public Integer getNumViajesVacio() {
        return numViajesVacio;
    }

    public void setNumViajesVacio(Integer numViajesVacio) {
        this.numViajesVacio = numViajesVacio;
    }

    public String getNumCambioLinea() {
        return numCambioLinea;
    }

    public void setNumCambioLinea(String numCambioLinea) {
        this.numCambioLinea = numCambioLinea;
    }

    public String getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(String totalHoras) {
        this.totalHoras = totalHoras;
    }

    public String getHorasVacio() {
        return horasVacio;
    }

    public void setHorasVacio(String horasVacio) {
        this.horasVacio = horasVacio;
    }

    public String getCosteTotalBuses() {
        return costeTotalBuses;
    }

    public void setCosteTotalBuses(String costeTotalBuses) {
        this.costeTotalBuses = costeTotalBuses;
    }

    public String getCosteHoraDesvio() {
        return costeHoraDesvio;
    }

    public void setCosteHoraDesvio(String costeHoraDesvio) {
        this.costeHoraDesvio = costeHoraDesvio;
    }

    public String getCosteCambiosLinea() {
        return costeCambiosLinea;
    }

    public void setCosteCambiosLinea(String costeCambiosLinea) {
        this.costeCambiosLinea = costeCambiosLinea;
    }

    public String getPlusCambiosLinea() {
        return plusCambiosLinea;
    }

    public void setPlusCambiosLinea(String plusCambiosLinea) {
        this.plusCambiosLinea = plusCambiosLinea;
    }

    public String getPlusAutobus() {
        return plusAutobus;
    }

    public void setPlusAutobus(String plusAutobus) {
        this.plusAutobus = plusAutobus;
    }

    public String getPlusKmVacio() {
        return plusKmVacio;
    }

    public void setPlusKmVacio(String plusKmVacio) {
        this.plusKmVacio = plusKmVacio;
    }

    public String getPlusKilometros() {
        return plusKilometros;
    }

    public void setPlusKilometros(String plusKilometros) {
        this.plusKilometros = plusKilometros;
    }

    public String getPlusExpVacio() {
        return plusExpVacio;
    }

    public void setPlusExpVacio(String plusExpVacio) {
        this.plusExpVacio = plusExpVacio;
    }

    public Programacion getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Programacion programacion) {
        this.programacion = programacion;
    }
}
