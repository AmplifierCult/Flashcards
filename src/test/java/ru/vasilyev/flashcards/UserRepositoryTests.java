package ru.vasilyev.flashcards;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.UserRepository;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    LoadDataBase loadDataBase;

    @BeforeEach
    void setUp() {
        loadDataBase.initUserDatabase();
    }

    @AfterEach
    void tearDown() {
        loadDataBase.cleanStatisticsDataBase();
        loadDataBase.cleanCardDataBase();
        loadDataBase.cleanUserDataBase();
        loadDataBase.cleanDeckDataBase();
    }

    @Test
    void baseCreateOperations() {
        User newUser = new User("Jack");
        newUser.setPassword("126");
        newUser.setRegistrationDate(Instant.now());
        newUser = userRepository.save(newUser);
        assertEquals(5, userRepository.count());
        assertEquals(newUser.getLogin(), userRepository.findByLogin("Jack").getLogin());
        assertEquals(newUser.getId(), userRepository.findById(newUser.getId()).get().getId());
    }

    @Test
    void baseUpdateOperations() {
        User updatedUser = userRepository.findAll().get(3);
        updatedUser.setLogin("Nick");
        userRepository.save(updatedUser);
        assertEquals(updatedUser.getId(), userRepository.findByLogin("Nick").getId());

    }

    @Test
    void baseDeleteOperations() {
        User user = userRepository.findByLogin("Igor");
        userRepository.delete(user);
        assertEquals(3, userRepository.count());
    }
}
