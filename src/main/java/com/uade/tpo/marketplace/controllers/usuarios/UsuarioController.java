package com.uade.tpo.marketplace.controllers.usuarios;

import com.uade.tpo.marketplace.entity.Usuario;
import com.uade.tpo.marketplace.exceptions.UsuarioDuplicadoException;
import com.uade.tpo.marketplace.exceptions.UsuarioNotFoundException;
import com.uade.tpo.marketplace.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public List<Usuario> getAllUsuarios(){
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable String id){
        System.out.println(id);
        return usuarioService.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con ID: " + id));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable String id, @RequestBody UsuarioUpdateRequest request) {
        try {
            Usuario usuario = usuarioService.updateUsuario(id, request);
            return ResponseEntity.ok(usuario);
        } catch (UsuarioDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/email/{email}") // Nueva ruta para buscar por email
    public Usuario getUsuarioByEmail(@PathVariable String email) {
        System.out.println("Buscando usuario por email: " + email);
        return usuarioService.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con email: " + email));
    }
}