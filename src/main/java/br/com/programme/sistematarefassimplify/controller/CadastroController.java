package br.com.programme.sistematarefassimplify.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.programme.sistematarefassimplify.core.model.User;
import br.com.programme.sistematarefassimplify.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class CadastroController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    ResponseEntity create(@RequestBody User usuario) {
        
        var user = service.findUser(usuario.getUsername());
        
        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuário já cadastrado!");
        }

        var passEncoded = BCrypt.withDefaults().hashToString(12, usuario.getPassword().toCharArray());
        usuario.setPassword(passEncoded);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.cadastrar(usuario));
    }

    @GetMapping
    List<User> getAll() {
        var array = service.findAll();
        return array;
    }
}
