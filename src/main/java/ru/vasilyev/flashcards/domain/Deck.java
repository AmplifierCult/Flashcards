package ru.vasilyev.flashcards.domain;

import java.time.Instant;
import java.util.List;

public class Deck {
    List<Card> deck;
    String deckName;
    Boolean sharedAccess;
    byte [] cover;
    Instant creationDate;
}
