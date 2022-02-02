package com.xoquin.app_db_c_estudios.vo;

import java.sql.Date;

public class Alumno {
    private int numExp;
    private String dni;
    private String nombre;
    private String apellidos;
    private Date fecha;

    public int getNumExp() {
        return this.numExp;
    }

    public void setNumExp(int numExp) {
        this.numExp = numExp;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Alumno() {
    }

}
