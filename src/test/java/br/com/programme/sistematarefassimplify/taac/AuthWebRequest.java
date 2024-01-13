package br.com.programme.sistematarefassimplify.taac;

import br.com.programme.sistematarefassimplify.config.security.FilterTaskAuth;
import br.com.programme.sistematarefassimplify.core.model.User;
import br.com.programme.sistematarefassimplify.service.UsuarioService;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthWebRequest {

    @Autowired
    @InjectMocks
    FilterTaskAuth filter;

    @Mock
    UsuarioService userService;

    @Autowired
    WebTestClient webClient;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void verificarLogin_quando_ReceberUsuario() {

        var user =  createUser();
        String authHeader = "Basic dXNlcmxvZ2luOnVzZXItcm9vdHB3ZA=="; //Cypher code for this user.

        var cypherUser = user;
        cypherUser.setPassword("$2a$12$8SdKDC6IHUtX8.0j47uTqeoMlNJFmIU9uxvd5BsGFya8/e.E.C7vm"); //The encoded password from user.

        when(userService.findUser(user.getUsername())).thenReturn(cypherUser);



        webClient.post()
                .uri("/auth")
                .header("Authorization", authHeader)
                .exchange()
                .expectStatus().isOk();



    }



    @Test
    void enviarResposta401_quando_UsuarioNaoFoiEncontrado() throws ParseException {

        String authHeader = "Basic dXNlcmxvZ2luOnVzZXItcm9vdHB3ZA=="; // The account not exist.

        var requestResult = webClient.post()
                .uri("/auth")
                .header("Authorization", authHeader)
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody().jsonPath("$.error").isEqualTo("Unauthorized");
    }


    @Test
    void enviarResposta401_quando_SenhaIncorreta() {

        var user = createUser("7oao", "client-pwd", "Joao");
        String authHeader = "Basic N29hbzp1c2VyLXJvb3Rwd2Q="; //This header has an incorrect password for the account.

        var cypherUser = user;
        cypherUser.setPassword("$2a$12$KxztkWqxId.EiOUQLK4D8erz2lE.j7LMlPYaz1xb4coMa6O5zHyrm"); // The encoded password from the user.
        when(userService.findUser(user.getUsername())).thenReturn(user);


        webClient.post()
                .uri("/auth")
                .header("Authorization", authHeader)
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody().jsonPath("$.error").isEqualTo("Unauthorized");



    }

    private Object getJson(byte[] responseByte) throws ParseException {

        var responseBody = new String(responseByte);

        responseBody = responseBody.replace("[", "");
        responseBody = responseBody.replace("]", "");

        var content = new JSONParser(responseBody).parse();

        //var dataResponse = (LinkedHashMap) content;

        return content;
    }


    User createUser() {

        return new User("userlogin", "user-rootpwd", "usermane");
    }

    User createUser(String username, String password, String name) {

        return new User(username, password, name);
    }
}