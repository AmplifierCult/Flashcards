package ru.vasilyev.flashcards;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.repository.CardRepository;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO сделать update тесты для каждого поля каждой сущности


@SpringBootTest
public class CardRepositoryTests {
    @Autowired
    CardRepository repository;

    @Autowired
    LoadDataBase loadDataBase;

    @BeforeEach
    void setUp() {
        loadDataBase.initCardDatabase();
    }

    @AfterEach
    void tearDown() {
        loadDataBase.cleanStatisticsDataBase();
        loadDataBase.cleanCardDataBase();
        loadDataBase.cleanUserDataBase();
        loadDataBase.cleanDeckDataBase();
    }

    @Test
    //@Transactional
    void baseCRUDOperations() {
        Card savedCard;

        //create
        savedCard = repository.save(new Card("Java"));
        assertEquals(5, repository.count());
        assertEquals(savedCard.getWord(), repository.findByWord("Java").getWord());
        assertEquals(savedCard.getId(), repository.findById(savedCard.getId()).get().getId());

        //update
        savedCard.setWord("learning");
        Map<String, String> exampleOfUse = new HashMap<>();
        exampleOfUse.put(savedCard.getWord(), "Java learning");
        savedCard.setExampleOfUse(exampleOfUse);
        repository.save(savedCard);
        assertEquals(savedCard.getId(), repository.findByWord("learning").getId());
        assertEquals(savedCard.getExampleOfUse(), repository.findById(savedCard.getId()).get().getExampleOfUse());

        //delete
        repository.delete(savedCard);
        assertEquals(4, repository.count());
    }
}
