package ru.vasilyev.flashcards.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.vasilyev.flashcards.domain.User;

import java.util.List;

/**
 * TODO Реализовать!
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);

    User findById(long id);
}
