package ru.vasilyev.flashcards.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.util.List;

@Entity
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    List<Card> deck;
    String deckName;
    Boolean sharedAccess;
    byte [] cover;
    Instant creationDate;

    protected Deck() {
    }

    public Long getId() {
        return id;
    }
}
