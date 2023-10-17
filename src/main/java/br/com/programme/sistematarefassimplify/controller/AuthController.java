package br.com.programme.sistematarefassimplify.controller;


import br.com.programme.sistematarefassimplify.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    void autentico() {
        System.out.println("Auth controller");
    }

}
