package ru.vasilyev.flashcards.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.domain.UserNotFoundException;
import ru.vasilyev.flashcards.repository.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

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

    public User updateUser(User user, @Positive Long id) {
        validateUser(user);
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

    public User getUserById(@Positive Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getUserByLogin(@NotBlank(message = "login cannot be empty") String login) {
        return userRepository.findByLogin(login);
    }

    public void deleteUserById(@Positive Long id) {
        userRepository.deleteById(id);
    }

    private void validateUser(User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        for (ConstraintViolation<User> violation : violations) {
            log.error(violation.getMessage());
        }
    }
}
