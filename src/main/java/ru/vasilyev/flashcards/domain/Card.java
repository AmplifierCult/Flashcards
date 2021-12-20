package ru.vasilyev.flashcards.domain;

import javax.persistence.*;
import java.time.Instant;
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

    Instant creationDate;

    protected Card() {
    }

    public Card(String word, User author) {
        this.word = word;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }

    public void setTranslatedWord(String translatedWord) {
        this.translatedWord = translatedWord;
    }

    public byte[] getExampleOfPronunciation() {
        return exampleOfPronunciation;
    }

    public void setExampleOfPronunciation(byte[] exampleOfPronunciation) {
        this.exampleOfPronunciation = exampleOfPronunciation;
    }

    public Map<String, String> getExampleOfUse() {
        return exampleOfUse;
    }

    public void setExampleOfUse(Map<String, String> exampleOfUse) {
        this.exampleOfUse = exampleOfUse;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", authorId=" + author.getId() +
                ", authorName=" + author.getLogin() +
                '}';
    }
}
