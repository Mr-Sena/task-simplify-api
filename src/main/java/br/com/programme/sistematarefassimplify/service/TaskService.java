package br.com.programme.sistematarefassimplify.service;

import br.com.programme.sistematarefassimplify.core.model.Tarefa;
import br.com.programme.sistematarefassimplify.infra.config.exception.BadRequestException;
import br.com.programme.sistematarefassimplify.infra.repo.TaskRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }


    public Tarefa newTask(Tarefa task) {
        return repository.save(task);
    }

    public List<Tarefa> listAll() {

        Sort sort = Sort.by("prioridade").descending()
                .and(Sort.by("nome").ascending());

        return repository.findAll(sort);
    }

    public Tarefa atualizar(Long id, Tarefa task) {

        repository.findById(id).ifPresentOrElse((existingTask) -> {
            task.setIdTable(id);
            repository.save(task);
        }, () -> {
            throw new BadRequestException("Task %d não existe! ".formatted(id));
        });

        return task;
    }

    public Object remove(Long id) {

        var task = repository.findById(id);

        if (task.isPresent()) {
            repository.deleteById(id);
            return task.get();

        }

        throw new BadRequestException("Task %d não existe!".formatted(id));


    }
}
