package ru.vasilyev.flashcards.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Deck_Card",
            joinColumns = {@JoinColumn(name = "Deck")},
            inverseJoinColumns = {@JoinColumn(name = "Card")}
    )
    @OrderColumn
    List<Card> deck;

    String deckName;
    Boolean sharedAccess;
    byte [] cover;
    Instant creationDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    User author;

    protected Deck() {
    }

    public Deck(String deckName, User author) {
        this.deckName = deckName;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public Boolean getSharedAccess() {
        return sharedAccess;
    }

    public void setSharedAccess(Boolean sharedAccess) {
        this.sharedAccess = sharedAccess;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deck deck = (Deck) o;

        return id.equals(deck.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Deck{" +
                "id=" + id +
                ", deckName='" + deckName + '\'' +
                ", sharedAccess=" + sharedAccess +
                ", creationDate=" + creationDate +
                ", authorId=" + author.getId() +
                ", authorName=" + author.getLogin() +
                '}';
    }
}
