package br.com.programme.sistematarefassimplify.taac;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.programme.sistematarefassimplify.core.model.User;
import br.com.programme.sistematarefassimplify.service.UsuarioService;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CadastroWebRequest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private UsuarioService service;

    @Test
    void lerTodosUsuarios_quando_PedidoEnviadoComSucesso() throws ParseException {

        var user = service.cadastrar(new User("userman", "string227-wpwd", "Joseph"));

        var requestResult = webClient.get()
                .uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].username").isEqualTo(user.getUsername())
                .jsonPath("$[0].password").isEqualTo(user.getPassword())
                .jsonPath("$[0].name").isEqualTo(user.getName())
                .returnResult();


        byte[] responseByte = requestResult.getResponseBody();
        User theUser = getCreatedUser(responseByte);

        service.remove(theUser.getIdUser());
    }



    @Test
    void CadastrarNovoUsuario_quando_ComandoEnviadoComSucesso() throws ParseException {

        var user = new User("userman", "string227-wpwd", "Joseph");

        var requestResult = webClient.post()
                .uri("/users")
                .bodyValue(user)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$").isMap()
                .jsonPath("$.name").isEqualTo(user.getName())
                .jsonPath("$.username").isEqualTo(user.getUsername())
                .returnResult();

        byte[] responseByte = requestResult.getResponseBodyContent();
        var theUser = getCreatedUser(responseByte);

        passwordVerifyer(user.getPassword(), theUser.getPassword());
        System.out.println(theUser.getPassword());

        //To ensure the idempontency
        service.remove(theUser.getIdUser());
    }


    @Test
    void NaoDeveCriarNovoUsuario_quando_CadastroJaExiste() {

        var user = new User("userman", "string227-wpwd", "Joseph");

        user.setPassword("$2a$12$oaPrek4Bp5zl9MXv44aOAeYXGqAKGDXDRndidp.WbavSoYE2b3RbO");

        service.cadastrar(user);

        webClient.post()
            .uri("/users")
            .bodyValue(user)
            .exchange()
            .expectStatus().isBadRequest();

        service.remove(user.getIdUser());

    }






    private void passwordVerifyer(String password, String encodedPwd) {

        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), encodedPwd);
        assertThat(passwordVerify.verified).isTrue();
    }


    private User getCreatedUser(byte[] responseByte) throws ParseException {

        var responseBody = new String(responseByte);

        responseBody = responseBody.replace("[", "");
        responseBody = responseBody.replace("]", "");

        var content = new JSONParser(responseBody).parse();

        var dataResponse = (LinkedHashMap) content;
        return fromJson(dataResponse);

    }

    private User fromJson(LinkedHashMap content) {
        var idDoUsuario = Long.valueOf(content.get("idUser").toString());

        var username = content.get("username").toString();
        var password = content.get("password").toString();
        var name = content.get("name").toString();

        return new User(idDoUsuario, username, password, name, LocalDateTime.now());
    }
}
