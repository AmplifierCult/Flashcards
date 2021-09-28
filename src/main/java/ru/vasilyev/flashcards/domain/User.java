package ru.vasilyev.flashcards.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String login;
    String password;
    String email;
    Instant registrationDate;
    Instant lastActionDate;
    List <Deck> decks;

    protected User() {
    }

    public User(String login) {
        this.login = login;
    }

    public Long getId() {
        return id;
    }
}
