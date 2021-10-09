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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    @MapKeyColumn(name = "map_key")
    @Column(name = "map_value")
    Map<String, String> exampleOfUse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    User author;
    byte [] exampleOfPronunciation;

    protected Card() {
    }

    public Card(String word) {
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Map<String, String> getExampleOfUse() {
        return exampleOfUse;
    }

    public void setExampleOfUse(Map<String, String> exampleOfUse) {
        this.exampleOfUse = exampleOfUse;
    }
}
