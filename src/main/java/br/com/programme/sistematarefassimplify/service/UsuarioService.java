package br.com.programme.sistematarefassimplify.service;

import br.com.programme.sistematarefassimplify.core.model.User;
import br.com.programme.sistematarefassimplify.infra.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private UserRepository repository;

    public UsuarioService(UserRepository repository) {
        this.repository = repository;
    }

    public User cadastrar(User usuario) {
        return repository.save(usuario);
    }

    public User findUser(String username) {
        var usuario = repository.findByUsername(username);
        return usuario;
    }

    public List<User> findAll() {
        return repository.findAll();
    }
}
