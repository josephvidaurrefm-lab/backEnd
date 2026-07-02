package com.Proyecto.backEnd.model.DTO;

public class RolMenuDTO {
    private int codm;
    private String nombre;
    private boolean asignado;

    public RolMenuDTO(int codm, String nombre, boolean asignado) {
        this.codm = codm;
        this.nombre = nombre;
        this.asignado = asignado;
    }

    public int getCodm() { return codm; }
    public String getNombre() { return nombre; }
    public boolean isAsignado() { return asignado; }

    public void setCodm(int codm) { this.codm = codm; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setAsignado(boolean asignado) { this.asignado = asignado; }
}