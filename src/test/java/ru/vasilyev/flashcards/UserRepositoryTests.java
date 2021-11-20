package ru.vasilyev.flashcards;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    UserRepository repository;

    @Autowired
    LoadDataBase loadDataBase;

    @BeforeEach
    void setUp() {
        loadDataBase.initUserDatabase();
    }

    @AfterEach
    void tearDown() {
        loadDataBase.cleanUserDataBase();
    }

    @Test
    void baseCRUDOperations() {
        User savedUser;

        //create
        savedUser = repository.save(new User("Jack"));
        assertEquals(5, repository.count());
        assertEquals(savedUser.getLogin(), repository.findByLogin("Jack").getLogin());
        assertEquals(savedUser.getId(), repository.findById(savedUser.getId()).get().getId());

        //update
        savedUser.setLogin("Mike");
        repository.save(savedUser);
        assertEquals(savedUser.getId(), repository.findByLogin("Mike").getId());

        //delete
        repository.delete(savedUser);
        assertEquals(4, repository.count());
    }
}
