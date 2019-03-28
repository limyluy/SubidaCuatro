package com.example.subidacuatro.Entidades;


import java.util.List;

public class Cliente {

    private String id;
    private String nombre;
    private String direccion;
    private String telefono;
    private boolean atrazado;
    private List<String> locales;

    public Cliente() {
    }

    public Cliente(String nombre, String direccion, String telefono, boolean atrazado, List<String> locales) {

        String id = "cliuno" + Math.random();

        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
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
