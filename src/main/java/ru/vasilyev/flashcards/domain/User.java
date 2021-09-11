package ru.vasilyev.flashcards.domain;

import java.time.Instant;
import java.util.List;

public class User {
    String login;
    String password;
    String email;
    Instant registrationDate;
    Instant lastActionDate;
    List <Deck> decks;
}
