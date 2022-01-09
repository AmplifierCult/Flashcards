package ru.vasilyev.flashcards.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vasilyev.flashcards.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
