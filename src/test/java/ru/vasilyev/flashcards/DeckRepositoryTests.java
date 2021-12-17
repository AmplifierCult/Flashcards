package ru.vasilyev.flashcards;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.DeckRepository;
import ru.vasilyev.flashcards.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class DeckRepositoryTests {
    @Autowired
    DeckRepository deckRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoadDataBase loadDataBase;

    @BeforeEach
    void setUp() {
        loadDataBase.initUserDatabase();
        loadDataBase.initCardDatabase();
        loadDataBase.initDeckDatabase();
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
        User author = userRepository.findByLogin("Andrey");
        Deck newDeck = new Deck("Coding", author);

        deckRepository.save(newDeck);
        assertEquals(5, deckRepository.count());
        assertEquals(newDeck.getDeckName(), deckRepository.findByDeckName("Coding").getDeckName());
        assertEquals(newDeck.getId(), deckRepository.findById(newDeck.getId()).get().getId());
    }

    @Test
    void baseUpdateOperations() {
        Deck deck = deckRepository.findAll().get(2);
        deck.setDeckName("Fishing");
        deckRepository.save(deck);
        assertEquals(deck.getId(), deckRepository.findByDeckName("Fishing").getId());
    }

    @Test
    void baseDeleteOperations() {
        Deck deck = deckRepository.findAll().get(3);
        deckRepository.delete(deck);
        assertEquals(3, deckRepository.count());
    }
}
