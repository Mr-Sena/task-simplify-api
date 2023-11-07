package br.com.programme.sistematarefassimplify.infra.repo;

import br.com.programme.sistematarefassimplify.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);

}
