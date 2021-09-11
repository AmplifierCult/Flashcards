package ru.vasilyev.flashcards.domain;

import java.util.List;

public class User {
    String login;
    String password;
    String email;
    String registrationDate;
    String lastActionDate;
    List <Deck> decks;
}
