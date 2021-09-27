package ru.vasilyev.flashcards.domain;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    byte [] image;
    String word;
    String translatedWord;
    Map<String, String> exampleOfUse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    User author;
    byte [] exampleOfPronunciation;

    public Card() {
    }

    public User getAuthor() {
        return author;
    }
}
