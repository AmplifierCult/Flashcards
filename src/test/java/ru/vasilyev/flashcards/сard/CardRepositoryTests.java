package ru.vasilyev.flashcards.сard;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vasilyev.flashcards.LoadDataBase;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.CardRepository;
import ru.vasilyev.flashcards.repository.UserRepository;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO сделать update тесты для каждого поля каждой сущности

@SpringBootTest
public class CardRepositoryTests {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoadDataBase loadDataBase;

    @BeforeEach
    void setUp() {
        loadDataBase.initUserDatabase();
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
    void baseCreateOperation() {
        User author = userRepository.findByLogin("Andrey");
        Card newCard = new Card("Java");
        newCard.setAuthor(author);
        newCard.setTranslatedWord("Ява");
        newCard.setCreationDate(Instant.now());
        Card savedCard = cardRepository.save(newCard);

        assertEquals(5, cardRepository.count());
        assertEquals(savedCard.getWord(), newCard.getWord());
        assertEquals(savedCard.getId(), newCard.getId());
    }

    @Test
    void baseUpdateOperation() {
        Card card = cardRepository.findByWord("Iron");
        Map<String, String> exampleOfUse = new HashMap<>();
        exampleOfUse.put(card.getWord(), "Humans stopped using stone because bronze and iron were superior materials.");
        card.setExampleOfUse(exampleOfUse);
        Card savedCard = cardRepository.save(card);
        assertEquals(card.getId(), cardRepository.findByWord("Iron").getId());
        assertEquals(card.getExampleOfUse(), savedCard.getExampleOfUse());
        assertEquals(4, cardRepository.count());
    }

    @Test
    void baseDeleteOperation() {
        Card card = cardRepository.findAll().get(3);
        cardRepository.delete(card);
        assertEquals(3, cardRepository.count());
    }
}
