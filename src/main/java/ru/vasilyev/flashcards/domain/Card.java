package ru.vasilyev.flashcards.domain;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    byte [] image;
    String word;
    String translatedWord;
    //Map<String, String> exampleOfUse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    User author;
    byte [] exampleOfPronunciation;

    protected Card() {
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }
}
