package ru.vasilyev.flashcards.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CardService cardService;

    public boolean authorize(User user) {
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



    public void deleteUser(User user) {
        validateUser(user);
        userRepository.delete(user);
    }

    private void validateUser(User user) {

    }

}
