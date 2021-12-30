package ru.vasilyev.flashcards.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.service.CardService;
import ru.vasilyev.flashcards.service.UserService;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Map;

@Component
public class StatisticsDTO {

    @Autowired
    CardService cardService;

    @Autowired
    UserService userService;

    private Long id;

    private Long userId;

    private Long cardId;

    private String lastRecurrenceDate;

    @NotBlank(message = "knowledgeLevel cannot be empty")
    private String knowledgeLevel;

    private Map<String, String> history;

    public StatisticsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getLastRecurrenceDate() {
        return lastRecurrenceDate;
    }

    public void setLastRecurrenceDate(String lastRecurrenceDate) {
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


}
