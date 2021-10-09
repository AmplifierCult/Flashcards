package ru.vasilyev.flashcards;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.repository.CardRepository;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CardRepositoryTests {
    @Autowired
    CardRepository repository;

    @Test
    //@Transactional
    void baseCRUDOperations() {
        Card savedCard;
        savedCard = repository.save(new Card("Java"));
        assertEquals(1, repository.count());
        assertEquals(savedCard.getWord(), repository.findByWord("Java").getWord());
        assertEquals(savedCard.getId(), repository.findById(savedCard.getId()).get().getId());

        savedCard.setWord("learning");
        Map<String, String> exampleOfUse = new HashMap<>();
        exampleOfUse.put(savedCard.getWord(), "Java learning");
        savedCard.setExampleOfUse(exampleOfUse);
        repository.save(savedCard);
        assertEquals(savedCard.getId(), repository.findByWord("learning").getId());
        assertEquals(savedCard.getExampleOfUse(), repository.findById(savedCard.getId()).get().getExampleOfUse());


        repository.delete(savedCard);
        assertEquals(0, repository.count());
    }
}
