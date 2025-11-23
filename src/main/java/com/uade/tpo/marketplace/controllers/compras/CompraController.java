package com.uade.tpo.marketplace.controllers.compras;

import com.uade.tpo.marketplace.controllers.items.CarritoRequest;
import com.uade.tpo.marketplace.entity.Compra;
import com.uade.tpo.marketplace.entity.Usuario;
import com.uade.tpo.marketplace.exceptions.CompraNotFoundException;
import com.uade.tpo.marketplace.service.CompraService;
import com.uade.tpo.marketplace.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;
    private final UsuarioService usuarioService;

    @Autowired
    public CompraController(CompraService compraService, UsuarioService usuarioService) {
        this.compraService = compraService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Compra> crearCompra(@RequestBody CompraRequest compraRequest) {
        Compra nuevaCompra = compraService.crearCompra(compraRequest);
        return new ResponseEntity<>(nuevaCompra, HttpStatus.CREATED);
    }

// En com.uade.tpo.marketplace.controllers.compras.CompraController.java

    @GetMapping("/")
    public ResponseEntity<List<CompraResponse>> listarComprasDeUsuario(Authentication authentication) {
        String email = authentication.getName();

        // 1. Buscar el usuario por email
        Optional<Usuario> usuarioOptional = usuarioService.findByEmail(email);

        // 2. Usar map/orElseGet para manejar la lista de compras de forma segura.
        List<CompraResponse> responseList = usuarioOptional
                .map(usuario -> {
                    // Si el usuario existe, obtener sus compras.
                    List<Compra> compras = compraService.findAllByUsuario(usuario);

                    // Mapear la lista de entidades Compra a CompraResponse
                    return compras.stream()
                            .map(CompraResponse::fromEntity)
                            .collect(Collectors.toList());
                })
                .orElseGet(
                        () -> List.of()
                );

        // 3. Devolver la respuesta (que será el listado mapeado o una lista vacía).
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/todas")
    public ResponseEntity<List<Compra>> getAllCompras() {
        List<Compra> compras = compraService.findAll();
        return ResponseEntity.ok(compras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> getCompraById(@PathVariable String id ) throws CompraNotFoundException {
        Compra compra = compraService.findById(id); // El servicio ya lanzará NotFoundException si no existe
        return ResponseEntity.ok(compra);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompra(@PathVariable String id) throws CompraNotFoundException {
        compraService.deleteCompra(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/carrito/agregar")
    public ResponseEntity<Compra> agregarAlCarrito(
            Authentication authentication,
            @RequestBody CarritoRequest carritoRequest) {

        String email = authentication.getName();
        Compra compra = compraService.agregarAlCarrito(email, carritoRequest);
        return ResponseEntity.ok(compra);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Compra> checkout(Authentication authentication) {
        String email = authentication.getName();
        Compra compraConfirmada = compraService.checkout(email);
        return ResponseEntity.ok(compraConfirmada);
    }

    @GetMapping("/carrito-activo")
    public ResponseEntity<CompraResponse> obtenerCarritoActivo(Authentication authentication) {
        String email = authentication.getName();

        Optional<Usuario> usuarioOptional = usuarioService.findByEmail(email);

        Optional<CompraResponse> carritoActivoResponse = usuarioOptional
                .flatMap(usuario -> compraService.findCarritoPendienteByUsuario(usuario))
                .map(CompraResponse::fromEntity);
        return carritoActivoResponse
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok(new CompraResponse()));
    }
}