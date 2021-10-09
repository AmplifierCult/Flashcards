package ru.vasilyev.flashcards;


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

    @Test
    void baseCRUDOperations() {
        Deck savedDeck;
        savedDeck = repository.save(new Deck("Coding"));
        assertEquals(1, repository.count());
        assertEquals(savedDeck.getDeckName(), repository.findByDeckName("Coding").getDeckName());
        assertEquals(savedDeck.getId(), repository.findById(savedDeck.getId()).get().getId());

        savedDeck.setDeckName("Fishing");
        repository.save(savedDeck);
        assertEquals(savedDeck.getId(), repository.findByDeckName("Fishing").getId());

        repository.delete(savedDeck);
        assertEquals(0, repository.count());
    }
}
