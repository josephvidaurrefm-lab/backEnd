package com.Proyecto.backEnd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="producto")
@Getter
@Setter
public class ProductoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idproducto")
    private Integer idProducto;

    @Column(name="nombre")
    private String nombre;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="preciocompra")
    private Double precioCompra;

    @Column(name="precioventa")
    private Double precioVenta;

    @Column(name="stock")
    private Integer stock;

    @Column(name="unidadmedida")
    private String unidadMedida;

    @Column(name="estado")
    private Integer estado;
}