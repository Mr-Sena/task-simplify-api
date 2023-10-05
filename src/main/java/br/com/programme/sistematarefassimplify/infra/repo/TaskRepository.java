package br.com.programme.sistematarefassimplify.infra.repo;

import br.com.programme.sistematarefassimplify.core.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Tarefa, Long> {

}
