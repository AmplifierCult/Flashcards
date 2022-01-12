package ru.vasilyev.flashcards.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.service.CardService;
import ru.vasilyev.flashcards.service.UserService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeckDTO {

    @Autowired
    CardService cardService;

    @Autowired
    UserService userService;

    private Long id;

    private List<CardDTO> deckDTO;

    private String deckName;
    private Boolean sharedAccess;
    private byte [] cover;
    private String creationDate;

    private Long authorId;

    public DeckDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CardDTO> getDeckDTO() {
        return deckDTO;
    }

    public void setDeckDTO(List<CardDTO> deckDTO) {
        this.deckDTO = deckDTO;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }


}
