package com.uade.tpo.marketplace.controllers.productos;

import com.uade.tpo.marketplace.entity.Producto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponse {

    private String id;
    private String nombre;
    private float valor;
    private String descripcion;
    private String foto;
    private int cantidad;
    private int descuento;
    private String estado;

    private String categoriaNombre;
    private String categoriaId;

    public static ProductoResponse fromEntity(Producto producto) {
        if (producto == null) {
            return null;
        }

        return ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .valor(producto.getValor())
                .descripcion(producto.getDescripcion())
                .foto(producto.getFoto())
                .cantidad(producto.getCantidad())
                .descuento(producto.getDescuento())
                .estado(producto.getEstado() != null ? producto.getEstado().name() : null) // Convierte Enum a String
                .categoriaNombre(producto.getCategoria() != null ? producto.getCategoria().getNombre() : null)
                .categoriaId(producto.getCategoria() != null ? producto.getCategoria().getId() : null)
                .build();
    }
}