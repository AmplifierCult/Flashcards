package ru.vasilyev.flashcards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.domain.UserNotFoundException;
import ru.vasilyev.flashcards.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public boolean authorize(User user) { //TODO реализовать
        validateUser(user);
        return false;
    }

    public User createUser(User user){
        validateUser(user);
        return userRepository.save(user);
    }

    public User updateUser(User user, Long id) {
        return userRepository.findById(id)
                .map(userForUpdate -> {
                    userForUpdate.setLogin(user.getLogin());
                    userForUpdate.setEmail(user.getEmail());
                    return userRepository.save(userForUpdate);
                })
                .orElseGet(() -> {
                    user.setId(id);
                    return userRepository.save(user);
                });
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getUserByLogin(String login) {
        validateLogin(login);
        return userRepository.findByLogin(login);
    }

    private void validateLogin(String login) {
        if(ObjectUtils.isEmpty(login)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 400. User did not enter a login.");
        }
    }


    public void deleteUserById(Long id) {
        validateId(id);
        userRepository.deleteById(id);
    }

    private void validateId(Long id) { //TODO реализовать
    }

    private void validateUser(User user) { //TODO реализовать

    }

}
