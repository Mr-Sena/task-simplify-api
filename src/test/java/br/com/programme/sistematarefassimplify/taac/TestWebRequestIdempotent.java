package br.com.programme.sistematarefassimplify.taac;

import br.com.programme.sistematarefassimplify.core.model.Tarefa;
import br.com.programme.sistematarefassimplify.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestWebRequestIdempotent {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private TaskService service;


    @Test
    void testReadTaskSuccess() {

        var task = new Tarefa("1 task", "descp task 1", false, 1);

        service.newTask(task);

        webClient.get()
                .uri("/tasks")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray() // Expression $ is the root content.
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].nome").isEqualTo(task.getNome())
                .jsonPath("$[0].descricao").isEqualTo(task.getDescricao())
                .jsonPath("$[0].realizado").isEqualTo(task.isRealizado())
                .jsonPath("$[0].prioridade").isEqualTo(task.getPrioridade());

    }

    @Test
    void testUpdateTaskFailure_When_idNotFound() {

        Tarefa newTask = new Tarefa("up Tarefa origin", "tarefa to-do", true, 1);

        webClient.put()
                .uri("/tasks/99")
                .bodyValue(newTask)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$").isEqualTo("Task 99 não existe!");
    }

}
