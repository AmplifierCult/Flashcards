package ru.vasilyev.flashcards.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.Map;

@Entity
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id")
    Card card;

    Instant lastRecurrenceDate;
    String knowledgeLevel;
    //Map<String, String> history;

    protected Statistics() {
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Card getCard() {
        return card;
    }
}
