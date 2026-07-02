package com.Proyecto.backEnd.model.DTO;

public class ProcesoMenuDTO {
    private int codp;
    private String nombre;
    private boolean asignado;

    public ProcesoMenuDTO(int codp, String nombre, boolean asignado) {
        this.codp = codp;
        this.nombre = nombre;
        this.asignado = asignado;
    }

    public int getCodp() { return codp; }
    public String getNombre() { return nombre; }
    public boolean isAsignado() { return asignado; }

    public void setCodp(int codp) { this.codp = codp; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setAsignado(boolean asignado) { this.asignado = asignado; }
}