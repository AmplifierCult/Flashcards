package ru.vasilyev.flashcards.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Deck_Card",
            joinColumns = {@JoinColumn(name = "Deck")},
            inverseJoinColumns = {@JoinColumn(name = "Card")}
    )
    @OrderColumn
    List<Card> deck;

    String deckName;
    Boolean sharedAccess;
    byte [] cover;
    Instant creationDate;

    protected Deck() {
    }

    public Deck(String deckName) {
        this.deckName = deckName;
    }

    public Long getId() {
        return id;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }
}
