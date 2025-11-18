package com.uade.tpo.marketplace.controllers.items;

import com.uade.tpo.marketplace.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private String id;
    private String productoId;
    private String productoNombre;
    private String productoFoto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public static ItemResponse fromEntity(Item item) {

        return new ItemResponse(
                item.getId(),
                item.getProducto().getId(),
                item.getProducto().getNombre(),
                item.getProducto().getFoto(),
                item.getCantidad(),
                item.getProducto().getValor(),
                item.getProducto().getValor() * item.getCantidad()
        );
    }
}