package br.com.programme.sistematarefassimplify.service;

import br.com.programme.sistematarefassimplify.core.model.User;
import br.com.programme.sistematarefassimplify.infra.config.exception.BadRequestException;
import br.com.programme.sistematarefassimplify.infra.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<User> usuario = repository.findByUsername(username);

        if (usuario.isEmpty())
            throw new BadRequestException("Usuário %s não existe.".formatted(username));

        return usuario.get();

    }

    public List<User> findAll() {
        return repository.findAll();
    }
}
