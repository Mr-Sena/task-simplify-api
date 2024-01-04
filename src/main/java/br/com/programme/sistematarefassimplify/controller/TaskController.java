package br.com.programme.sistematarefassimplify.controller;

import br.com.programme.sistematarefassimplify.core.model.Tarefa;
import br.com.programme.sistematarefassimplify.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//OpenApi --> http://localhost:8080/swagger-ui.html [swagger link]


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;
    

    @PostMapping
    ResponseEntity create(@Valid @RequestBody Tarefa task) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.newTask(task));
    }



    @GetMapping
    List<Tarefa> list() {
        return service.listAll();
    }


    @PutMapping("{id}")
    ResponseEntity<Tarefa> update (@PathVariable Long id, @RequestBody Tarefa task) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(service.atualizar(id, task));

    }


    @DeleteMapping("/{id}")
    ResponseEntity delete (@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(service.remove(id));

    }

}
