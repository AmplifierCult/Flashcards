package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
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

    @GetMapping("/users/{id}")
    User getUserById(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/users/search")
    User getUserByLogin(@RequestParam(value = "login", required = false) String login) {
        if(ObjectUtils.isEmpty(login)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 400. User don't write a login.");
        }
        return repository.findByLogin(login);
    }

//    @PostMapping("/users")
//    User createUser(@RequestParam(value = "login", required = true) String login) {
//
//        return repository.save(new User(login));
//    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }
}
