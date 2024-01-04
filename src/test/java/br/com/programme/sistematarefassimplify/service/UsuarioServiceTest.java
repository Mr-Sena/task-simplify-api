package br.com.programme.sistematarefassimplify.service;

import br.com.programme.sistematarefassimplify.core.model.User;
import br.com.programme.sistematarefassimplify.infra.config.exception.BadRequestException;
import br.com.programme.sistematarefassimplify.infra.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class UsuarioServiceTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    @Autowired
    UsuarioService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void shouldCreateNewUser_when_SendRequestNewUser() {

        var usuario = createUser();

        when(repository.save(usuario)).thenReturn(usuario);

        var theUser = service.cadastrar(usuario);

        userAssert(usuario, theUser);
    }

    @Test
    void shouldReturnUser_when_SendUserRequest() {

        var usuario = createUser();

        when(repository.findByUsername(usuario.getUsername())).thenReturn(Optional.of(usuario));

        var theUser = service.findUser(usuario.getUsername());

        userAssert(usuario, theUser);

    }


    @Test
    void shoudReturnTheUserList_when_SendUserRequest() {

        List<User> array = new ArrayList<>();

        when(repository.findAll()).thenReturn(array);

        var list = service.findAll();

        assertThat(list).isInstanceOf(List.class);
    }





    @Test
    void shouldReturnNull_when_UserNotFound() {

        var username = "userman";
        when(repository.findByUsername(username)).thenReturn(Optional.empty());

        var theUser = service.findUser(username);

        assertThat(theUser).isEqualTo(null);
    }



    @Test
    void shouldNotRemoveUser_when_UserNotExist() {

        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(BadRequestException.class,
                () -> service.remove(id));
    }



    User createUser() {

        User user = new User("userlogin", "user-rootpwd", "John");
        return user;
    }

    void userAssert(User expected, User actual) {

        assertThat(expected.getIdUser()).isEqualTo(actual.getIdUser());
        assertThat(expected.getName()).isEqualTo(actual.getName());
        assertThat(expected.getUsername()).isEqualTo(actual.getUsername());
        assertThat(expected.getCreatedAt()).isEqualTo(actual.getCreatedAt());
    }
}