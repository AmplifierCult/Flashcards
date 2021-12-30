package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.dto.UserDTO;
import ru.vasilyev.flashcards.dto.mapper.UserMapper;
import ru.vasilyev.flashcards.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * TODO написать тесты для RestController
 */
@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @GetMapping("/users")
    List<UserDTO> all() {
        return userService.getAllUsers().stream().map(UserMapper.MAPPER::mapToUserDTO).collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable Long id) {
        return UserMapper.MAPPER.mapToUserDTO(userService.getUserById(id));
    }

    @GetMapping("/users/search")
    UserDTO getUserByLogin(@RequestParam(value = "login", required = false) String login) {
        return UserMapper.MAPPER.mapToUserDTO(userService.getUserByLogin(login));
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    UserDTO newUser(@RequestBody UserDTO newUserDTO) {
        User newUser = UserMapper.MAPPER.mapToUser(newUserDTO);
        User createdUser = userService.createUser(newUser);
        return UserMapper.MAPPER.mapToUserDTO(createdUser);
    }

    @PutMapping("/users/{id}")
    UserDTO replaceUser(@RequestBody UserDTO newUserDTO, @PathVariable Long id) {
        User newUser = UserMapper.MAPPER.mapToUser(newUserDTO);
        return UserMapper.MAPPER.mapToUserDTO(userService.updateUser(newUser, id));
    }

    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
