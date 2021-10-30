package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.vasilyev.flashcards.domain.LoadDataBase;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.domain.UserNotFoundException;
import ru.vasilyev.flashcards.repository.UserRepository;

import java.util.List;

/**
 * TODO Реализовать базовые операции!
 * TODO Описать базовые CRUD (Create, Update, Delete) операции для всех сущностей
 * TODO отослать запросы с помощью POSTMan
 * TODO написать тесты для RestController
 */
@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(LoadDataBase.class);

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }

    @PostMapping("/users")
    User createUser(@RequestParam(value = "login", required = true) String login) {
        repository.save(new User(login));
        return repository.findByLogin(login);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
