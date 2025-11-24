package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controllers.productos.ProductoRequest;
import com.uade.tpo.marketplace.controllers.productos.ProductoResponse;
import com.uade.tpo.marketplace.controllers.productos.ProductoUpdateRequest;
import com.uade.tpo.marketplace.entity.Categoria;
import com.uade.tpo.marketplace.entity.Producto;
import com.uade.tpo.marketplace.exceptions.ProductoDuplicadoException;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    Producto crearProducto(ProductoRequest productoRequest) throws ProductoDuplicadoException;

    List<ProductoResponse> getProductos();

    List<ProductoResponse> getTodosProductos();
    Producto findById(String id);

    Optional<Producto> findByNombre(String nombre);

    List<ProductoResponse> findByCategoria(Categoria categoria);

    List<ProductoResponse> findByCategoriaId(String categoriaId);

    List<ProductoResponse> findByCategoriaIdWithValidation(String categoriaId);

    Producto actualizarProducto(String id, ProductoUpdateRequest producto);

    void desactivarProducto(String id);
    void activarProducto(String id);

    List<ProductoResponse> buscarPorNombreOCategoria(String query);
    List<ProductoResponse> findByCategoriaNombre(String nombreCategoria);
}
