package com.example.firma;

public class signaturess {
    public Integer id;
    public String descripcion;
    public String firma;

    public signaturess(){

    }

    public signaturess(Integer id, String descripcion, String firma) {
        this.id = id;
        this.descripcion = descripcion;
        this.firma = firma;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
