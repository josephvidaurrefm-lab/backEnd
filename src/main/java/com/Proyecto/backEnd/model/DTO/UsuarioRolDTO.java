package com.Proyecto.backEnd.model.DTO;

public class UsuarioRolDTO {
    private int codr;
    private String nombre;
    private boolean asignado;

    public UsuarioRolDTO(int codr, String nombre, boolean asignado) {
        this.codr = codr;
        this.nombre = nombre;
        this.asignado = asignado;
    }

    public int getCodr() { return codr; }
    public String getNombre() { return nombre; }
    public boolean isAsignado() { return asignado; }

    public void setCodr(int codr) { this.codr = codr; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setAsignado(boolean asignado) { this.asignado = asignado; }
}
