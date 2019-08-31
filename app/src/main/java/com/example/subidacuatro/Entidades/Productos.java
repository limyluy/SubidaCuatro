package com.example.subidacuatro.Entidades;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Productos {

    private String id;
    private String nombre;
    private String descripcion;
    private String marca;
    private String imgProducto;
    private String infAdicional;
    private String desAdicional;
    private Boolean oferta;
    private ArrayList<String> categorias;
    private ArrayList<String> localesTiene;
    private long vesesBuscado;


    public Productos() {
    }

    public Productos(String nombre, String descripcion, String marca, String imgProducto, String infAdicional, String desAdicional, Boolean oferta, ArrayList<String> categorias, ArrayList<String> localesTiene, long vesesBuscado) {

        Date tiempoActual = Calendar.getInstance().getTime();
        String producto = nombre + String.valueOf(tiempoActual.getDate());
        producto.replaceAll(" ","_");

        this.id = producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.imgProducto = imgProducto;
        this.infAdicional = infAdicional;
        this.desAdicional = desAdicional;
        this.oferta = oferta;
        this.categorias = categorias;
        this.localesTiene = localesTiene;
        this.vesesBuscado = vesesBuscado;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getImgProducto() {
        return imgProducto;
    }

    public void setImgProducto(String imgProducto) {
        this.imgProducto = imgProducto;
    }

    public String getInfAdicional() {
        return infAdicional;
    }

    public void setInfAdicional(String infAdicional) {
        this.infAdicional = infAdicional;
    }

    public String getDesAdicional() {
        return desAdicional;
    }

    public void setDesAdicional(String desAdicional) {
        this.desAdicional = desAdicional;
    }

    public Boolean getOferta() {
        return oferta;
    }

    public void setOferta(Boolean oferta) {
        this.oferta = oferta;
    }

    public ArrayList<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<String> categorias) {
        this.categorias = categorias;
    }

    public ArrayList<String> getLocalesTiene() {
        return localesTiene;
    }

    public void setLocalesTiene(ArrayList<String> localesTiene) {
        this.localesTiene = localesTiene;
    }

    public long getVesesBuscado() {
        return vesesBuscado;
    }

    public void setVesesBuscado(long vesesBuscado) {
        this.vesesBuscado = vesesBuscado;
    }
}
