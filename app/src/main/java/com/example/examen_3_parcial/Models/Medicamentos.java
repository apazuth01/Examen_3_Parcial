package com.example.examen_3_parcial.Models;

public class Medicamentos {
    private int id;
    private String Descripcion;
    private String Cantidad;
    private String Tiempo;
    private byte[] Imagen;
    private String Periocidad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getPeriocidad() {
        return Periocidad;
    }

    public void setPeriocidad(String periocidad) {
        Periocidad = periocidad;
    }
    public String getTiempo() {
        return Tiempo;
    }

    public void setTiempo(String tiempo) {
        Tiempo = tiempo;
    }
    public byte[] getImagen() {
        return Imagen;
    }

    public void setImagen(byte[] imagen) {
        Imagen = imagen;
    }
}
