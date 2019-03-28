package com.example.subidacuatro.Entidades;

public class Historial {

    private String id;
    private String accion;
    private String sujeto;
    private String fecha;
    private String detalles;


    public Historial() {
    }

    public Historial(String accion, String sujeto, String fecha, String detalles) {

        String id = "hisuno"+Math.random();
        this.id = id;
        this.accion = accion;
        this.sujeto = sujeto;
        this.fecha = fecha;
        this.detalles = detalles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getSujeto() {
        return sujeto;
    }

    public void setSujeto(String sujeto) {
        this.sujeto = sujeto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}
