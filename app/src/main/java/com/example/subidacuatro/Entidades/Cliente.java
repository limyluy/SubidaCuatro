package com.example.subidacuatro.Entidades;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Cliente {

    private String id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String identificacion;
    private boolean atrazado;
    private List<String> locales;

    public Cliente() {
    }


    public Cliente(String nombre, String direccion, String telefono, String identificacion, boolean atrazado, List<String> locales) {

        Date tiempoActual = Calendar.getInstance().getTime();
        String cliente = nombre + String.valueOf(tiempoActual.getDate());

        this.id = cliente.replaceAll(" ","_");
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.identificacion = identificacion;
        this.atrazado = atrazado;
        this.locales = locales;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public boolean isAtrazado() {
        return atrazado;
    }

    public void setAtrazado(boolean atrazado) {
        this.atrazado = atrazado;
    }

    public List<String> getLocales() {
        return locales;
    }

    public void setLocales(List<String> locales) {
        this.locales = locales;
    }
}
