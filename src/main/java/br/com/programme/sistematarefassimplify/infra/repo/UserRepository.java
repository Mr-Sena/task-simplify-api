package br.com.programme.sistematarefassimplify.infra.repo;

import br.com.programme.sistematarefassimplify.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);

}
