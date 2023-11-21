package br.com.programme.sistematarefassimplify.infra.repo;

import br.com.programme.sistematarefassimplify.core.model.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository repository;

    @Test
    @DisplayName("Deve encontrar o username para o usuário com sucesso.")
    void findByUsernameSuccessfully() {

        var username = "userman";
        var user = newUser(username);

        Optional<User> usuario = repository.findByUsername(username);

        assertThat(usuario.isPresent()).isTrue();
        assertThat(user.getIdUser()).isEqualTo(usuario.get().getIdUser());
        assertThat(user.getName()).isEqualTo(usuario.get().getName());
        assertThat(user.getPassword()).isEqualTo(usuario.get().getPassword());

    }

    @Test
    @DisplayName("Unvailable username")
    void usuarioNaoEncontrado_quandoNaoPossuirCadastro() {

        Optional<User> usuario = repository.findByUsername("userman");

        assertThat(usuario.isPresent()).isFalse();


    }

    private User newUser(String username) {

        User user = new User(username, "cypher-pwd", "usuario");

        entityManager.persist(user);
        return user;
    }

}