package com.uade.tpo.marketplace.controllers.productos;

import com.uade.tpo.marketplace.entity.Categoria;
import lombok.Data;

@Data
public class ProductoUpdateRequest {
    private String nombre;
    private String descripcion;
    private Categoria categoria;
    private Float valor;
    private Integer cantidad;
    private Integer descuento;
}