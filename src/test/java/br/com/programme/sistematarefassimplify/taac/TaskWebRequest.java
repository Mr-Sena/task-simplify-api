package br.com.programme.sistematarefassimplify.taac;

import br.com.programme.sistematarefassimplify.core.model.Tarefa;
import br.com.programme.sistematarefassimplify.service.TaskService;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigInteger;
import java.util.LinkedHashMap;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TaskWebRequest {


	@Autowired
	private WebTestClient webClient;

	@Autowired
	private TaskService service;


	@Test
	void testReadTaskSuccess() throws ParseException {

		var task = new Tarefa("1 task", "descp task 1", false, 1);

		service.newTask(task);


		var requestResult = webClient.get()
				.uri("/tasks")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray() // Expression $ is the root content.
				.jsonPath("$.length()").isEqualTo(1)
				.jsonPath("$[0].nome").isEqualTo(task.getNome())
				.jsonPath("$[0].descricao").isEqualTo(task.getDescricao())
				.jsonPath("$[0].realizado").isEqualTo(task.isRealizado())
				.jsonPath("$[0].prioridade").isEqualTo(task.getPrioridade())
				.returnResult();

		byte[] responseByte = requestResult.getResponseBody();
		Tarefa theTask = getCreatedTask(responseByte);

		//To ensure the idempontency
		service.remove(theTask.getIdTask());

	}

	@Test
	void testCreateTaskSuccess() throws ParseException {
		var task = new Tarefa("Task one", "desc task 1", false, 1);

		var requestResult = webClient.post()
				.uri("/tasks")
				.bodyValue(task)
				.exchange()
				.expectStatus().isCreated()
				.expectBody()
				.jsonPath("$").isMap() // Expression $ is the root content.
				.jsonPath("$.nome").isEqualTo(task.getNome())
				.jsonPath("$.descricao").isEqualTo(task.getDescricao())
				.jsonPath("$.realizado").isEqualTo(task.isRealizado())
				.jsonPath("$.prioridade").isEqualTo(task.getPrioridade())
				.returnResult();

		byte[] responseByte = requestResult.getResponseBodyContent();
		var theTask = getCreatedTask(responseByte);

		//To ensure the idempontency
		service.remove(theTask.getIdTask());
	}



	@Test
	void testUpdateTaskSuccess() {

		Tarefa task = new Tarefa("Tarefa origin", "tarefa to-do", false, 1);

		service.newTask(task);

		Tarefa newTask = new Tarefa("up Tarefa origin", "tarefa to-do", true, 1);

		webClient.put()
				.uri("/tasks/%d".formatted(task.getIdTask()))
				.bodyValue(newTask)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isMap()
				.jsonPath("$.nome").isEqualTo(newTask.getNome())
				.jsonPath("$.descricao").isEqualTo(newTask.getDescricao())
				.jsonPath("$.prioridade").isEqualTo(newTask.getPrioridade())
				.jsonPath("$.realizado").isEqualTo(newTask.isRealizado());


		service.remove(task.getIdTask());

	}

	@Test
	void testDeleteTaskSuccess() {
		Tarefa task = new Tarefa("Tarefa origin", "tarefa to-do", false, 1);

		service.newTask(task);

		webClient.delete()
				.uri("/tasks/%d".formatted(task.getIdTask()))
				.exchange()
				.expectStatus().isOk();
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
	void testDeleteTaskFailure_When_idNotFound() {

		Tarefa newTask = new Tarefa("Tarefa origin", "tarefa to-do", true, 1);

		webClient.delete()
				.uri("/tasks/1")
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").isEqualTo("Task 1 não existe!");

	}





	private Tarefa getCreatedTask(byte[] responseByte) throws ParseException{

		var responseBody = new String(responseByte);

		responseBody = responseBody.replace("[", "");
		responseBody = responseBody.replace("]", "");


		var content = new JSONParser(responseBody).parse();

		var dataResponse = (LinkedHashMap) content;
		var theTask = fromJson(dataResponse);

		return theTask;
	}

	public Tarefa fromJson(LinkedHashMap content) {

		BigInteger id = (BigInteger) content.get("idTask");
		Long idTask = Long.valueOf(id.toString());

		BigInteger priority = (BigInteger) content.get("prioridade");
		int prioridade = Integer.valueOf(priority.toString());

		var task = new Tarefa(idTask, content.get("nome").toString(), content.get("descricao").toString(),
				(boolean) content.get("realizado"), prioridade);

		return task;
	}



}
