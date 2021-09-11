package ru.vasilyev.flashcards.domain;

import java.time.Instant;
import java.util.Map;

public class Statistics {
    User user;
    Card card;
    Instant lastRecurrenceDate;
    String knowledgeLevel;
    Map<String, String> history;
}
