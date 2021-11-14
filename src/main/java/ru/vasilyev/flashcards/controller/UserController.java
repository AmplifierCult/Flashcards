package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.domain.UserNotFoundException;
import ru.vasilyev.flashcards.repository.UserRepository;

import java.net.http.HttpResponse;
import java.util.List;

/**
 *
 * TODO написать тесты для RestController
 */
@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 400. User did not enter a login.");
        }
        return repository.findByLogin(login);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return repository.findById(id)
                .map(user -> {
                    user.setLogin(newUser.getLogin());
                    user.setEmail(newUser.getEmail());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
