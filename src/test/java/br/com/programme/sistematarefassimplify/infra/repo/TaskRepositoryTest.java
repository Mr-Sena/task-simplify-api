package br.com.programme.sistematarefassimplify.infra.repo;

import br.com.programme.sistematarefassimplify.core.model.Tarefa;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest // denote to the Spring that is a JPA Repository test class
@ActiveProfiles("test") // Denote to the Spring that this class uses the application-test.properties config from the test resources
class TaskRepositoryTest {


    @Autowired
    EntityManager entityManager;

    @Autowired
    TaskRepository repository;

    @Test
    @DisplayName("Should find task by id successfully")
    void findTaskByIdSuccessfully() {

        Tarefa theTask = createTask();

        Optional<Tarefa> task = repository.findById(theTask.getIdTask());

        assertThat(task.isPresent()).isTrue();
        assertThat(task.get().getIdTask()).isEqualTo(theTask.getIdTask());
        assertThat(task.get().getNome()).isEqualTo(theTask.getNome());
        assertThat(task.get().getPrioridade()).isEqualTo(theTask.getPrioridade());
        assertThat(task.get().isRealizado()).isEqualTo(theTask.isRealizado());
        assertThat(task.get().getDescricao()).isEqualTo(theTask.getDescricao());


    }

    @Test
    @DisplayName("Task no found when it does not exist")
    void searchForTask_whenItDoesExist() {

        Optional<Tarefa> task = repository.findById(99L);
        assertThat(task.isPresent()).isFalse();
    }

    private Tarefa createTask() {

        var theTask = new Tarefa("Buy store", "Compras para o mercado.", false, 2);
        entityManager.persist(theTask);
        return theTask;

    }
}