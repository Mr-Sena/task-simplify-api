package br.com.programme.sistematarefassimplify.service;

import br.com.programme.sistematarefassimplify.core.model.Tarefa;
import br.com.programme.sistematarefassimplify.config.exception.BadRequestException;
import br.com.programme.sistematarefassimplify.infra.repo.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    @Mock
    private TaskRepository repository;

    @InjectMocks // uses the mocks for this service dependencies...
    @Autowired
    private TaskService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void newTaskSuccessfully() {
        Tarefa tarefa = getNewTask();

        when(repository.save(tarefa)).thenReturn(tarefa);

        var theTask = service.newTask(tarefa);

        assertThat(tarefa.getIdTask()).isEqualTo(theTask.getIdTask());
        assertThat(tarefa.getNome()).isEqualTo(theTask.getNome());
        assertThat(tarefa.isRealizado()).isEqualTo(theTask.isRealizado());
        assertThat(tarefa.getDescricao()).isEqualTo(theTask.getDescricao());
        assertThat(tarefa.getPrioridade()).isEqualTo(theTask.getPrioridade());


    }

    @Test
    void listAllTest() {

        var task = getNewTask();
        var response = Arrays.asList(task);
        //create list single value --> var response = List.of(task);

        when(repository.findAll(Mockito.isA(Sort.class))).thenReturn(response);

        var list = service.listAll();

        assertThat(response.size()).isEqualTo(list.size());
        assertThat(response.get(0).getIdTask()).isEqualTo(list.get(0).getIdTask());
        assertThat(response.get(0).getNome()).isEqualTo(list.get(0).getNome());
        assertThat(response.get(0).getDescricao()).isEqualTo(list.get(0).getDescricao());
        assertThat(response.get(0).getPrioridade()).isEqualTo(list.get(0).getPrioridade());
        assertThat(response.get(0).isRealizado()).isEqualTo(list.get(0).isRealizado());


    }

    @Test
    void testUpdate() {

        var task = getNewTask();
        var idTarefa = 1L;

        when(repository.findById(idTarefa)).thenReturn(Optional.of(task));

        var response = service.atualizar(idTarefa, task);

        verify(repository, times(1)).save(task);

        assertThat(response.getIdTask()).isEqualTo(task.getIdTask());
        assertThat(response.getNome()).isEqualTo(task.getNome());
        assertThat(response.getPrioridade()).isEqualTo(task.getPrioridade());
        assertThat(response.getDescricao()).isEqualTo(task.getDescricao());
        assertThat(response.isRealizado()).isEqualTo(task.isRealizado());

    }

    @Test
    void removeTaskTest() {

        var tarefa = getNewTask();
        var idTask = 3L;

        when(repository.findById(idTask)).thenReturn(Optional.of(tarefa));

        var result = service.remove(idTask);

        verify(repository, times(1)).deleteById(idTask);
        assertThat(idTask).isEqualTo(result.getIdTask());


    }


    @Test
    void removeTaskTestFailed_when_taskNotExists() {

        var idTask = 7L;

        when(repository.findById(idTask)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> {
            service.remove(idTask);
        }, "Task %d não existe!".formatted(idTask));

    }







    @Test
    void testUpdateFailed_when_taskNotFound() {

        var tarefa = getNewTask();
        var taskId = 2L;

        when(repository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> {
            service.atualizar(taskId, tarefa);
        }, "Task %d não existe!".formatted(taskId));


    }




    private static Tarefa getNewTask() {
        return new Tarefa(3L, "Buy store", "Compras para o mercado.", false, 2);
    }
}