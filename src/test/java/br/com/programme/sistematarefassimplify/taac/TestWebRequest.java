package br.com.programme.sistematarefassimplify.taac;

import br.com.programme.sistematarefassimplify.core.model.Tarefa;
import br.com.programme.sistematarefassimplify.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestWebRequest {

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
				.uri("/tasks/1")
				.bodyValue(newTask)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").isEqualTo("Task 1 não existe!");
	}

	@Test
	void testCreateTaskSuccess() {
		var task = new Tarefa("Task one", "desc task 1", false, 1);

		webClient.post()
				.uri("/tasks")
				.bodyValue(task)
				.exchange()
				.expectStatus().isCreated()
				.expectBody()
				.jsonPath("$").isMap() // Expression $ is the root content.
				.jsonPath("$.nome").isEqualTo(task.getNome())
				.jsonPath("$.descricao").isEqualTo(task.getDescricao())
				.jsonPath("$.realizado").isEqualTo(task.isRealizado())
				.jsonPath("$.prioridade").isEqualTo(task.getPrioridade());


				/* To find query test
				.jsonPath("$").isArray() // Expression $ is the root content.
				.jsonPath("$.lenght()").isEqualTo(1)
				.jsonPath("$[0].nome").isEqualTo(task.getNome())
				.jsonPath("$[0].descricao").isEqualTo(task.getDescricao())
				.jsonPath("$[0].realizado").isEqualTo(task.isRealizado())
				.jsonPath("$[0].prioridade").isEqualTo(task.getPrioridade());
*/



	}


	@Test
	void testCreateTaskFailure_When_hasBadRequest() {

		var task = new Tarefa("", "", false, 0);

		webClient.post()
				.uri("/tasks")
				.bodyValue(task)
				.exchange()
				.expectStatus().isBadRequest();
	}


	@Test
	void testUpdateTaskSuccess() {

		Tarefa task = new Tarefa("Tarefa origin", "tarefa to-do", false, 1);

		service.newTask(task);

		Tarefa newTask = new Tarefa("up Tarefa origin", "tarefa to-do", true, 1);

		webClient.put()
				.uri("/tasks/1")
				.bodyValue(newTask)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isMap()
				.jsonPath("$.nome").isEqualTo(newTask.getNome())
				.jsonPath("$.descricao").isEqualTo(newTask.getDescricao())
				.jsonPath("$.prioridade").isEqualTo(newTask.getPrioridade())
				.jsonPath("$.realizado").isEqualTo(newTask.isRealizado());

	}

	@Test
	void testDeleteTaskSuccess() {
		Tarefa task = new Tarefa("Tarefa origin", "tarefa to-do", false, 1);

		service.newTask(task);

		webClient.delete()
				.uri("/tasks/1")
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void testDeleteTaskFailure_When_idNotFound() {

		Tarefa newTask = new Tarefa("Tarefa origin", "tarefa to-do", true, 1);

		webClient.delete()
				.uri("/tasks/1")
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").isEqualTo("Task 1 não existe!");

	}

}
