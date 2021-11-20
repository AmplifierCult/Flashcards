package ru.vasilyev.flashcards;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.repository.DeckRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class DeckRepositoryTests {
    @Autowired
    DeckRepository repository;

    @Autowired
    LoadDataBase loadDataBase;

    @BeforeEach
    void setUp() {
        loadDataBase.initDeckDatabase();
    }

    @AfterEach
    void tearDown() {
        loadDataBase.cleanDeckDataBase();
    }

    @Test
    void baseCRUDOperations() {
        Deck savedDeck;

        //create
        savedDeck = repository.save(new Deck("Coding"));
        assertEquals(5, repository.count());
        assertEquals(savedDeck.getDeckName(), repository.findByDeckName("Coding").getDeckName());
        assertEquals(savedDeck.getId(), repository.findById(savedDeck.getId()).get().getId());

        //update
        savedDeck.setDeckName("Fishing");
        repository.save(savedDeck);
        assertEquals(savedDeck.getId(), repository.findByDeckName("Fishing").getId());

        //delete
        repository.delete(savedDeck);
        assertEquals(4, repository.count());
    }
}
