package ru.vasilyev.flashcards.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

@Entity
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id")
    Card card;

    Instant lastRecurrenceDate;
    String knowledgeLevel;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    @MapKeyColumn(name = "map_key")
    @Column(name = "map_value")
    Map<String, String> history;

    protected Statistics() {
    }

    public Statistics(String knowledgeLevel, User user, Card card) {
        this.knowledgeLevel = knowledgeLevel;
        this.user = user;
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card cardId) {
        this.card = card;
    }

    public Instant getLastRecurrenceDate() {
        return lastRecurrenceDate;
    }

    public void setLastRecurrenceDate(Instant lastRecurrenceDate) {
        this.lastRecurrenceDate = lastRecurrenceDate;
    }

    public String getKnowledgeLevel() {
        return knowledgeLevel;
    }

    public void setKnowledgeLevel(String knowledgeLevel) {
        this.knowledgeLevel = knowledgeLevel;
    }

    public Map<String, String> getHistory() {
        return history;
    }

    public void setHistory(Map<String, String> history) {
        this.history = history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Statistics that = (Statistics) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", userName=" + user.getLogin() +
                ", cardId=" + card.getId() +
                ", word=" + card.getWord() +
                ", lastRecurrenceDate=" + lastRecurrenceDate +
                ", knowledgeLevel='" + knowledgeLevel + '\'' +
                '}';
    }
}
