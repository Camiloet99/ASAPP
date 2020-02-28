package com.example.usuario.needinghelp;

public class Peticion {
    private int lat, lon;
    private String nombre, descripcion;

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
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

    public Peticion(int la, int lo, String n, String desc ){
        this.lat = la;
        this.lon = lo;
        this.nombre = n;
        this.descripcion = desc;
    }

    public Peticion(){

    }


}
