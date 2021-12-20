package ru.vasilyev.flashcards.domain.dto;

import ru.vasilyev.flashcards.domain.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.Map;

public class CardDTO {
    private Long id;

    byte [] image;
    String word;
    String translatedWord;

    Map<String, String> exampleOfUse;

    Long authorId;
    byte [] exampleOfPronunciation;

    Instant creationDate;
}
