package ru.vasilyev.flashcards.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.service.CardService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeckDTO {

    @Autowired
    CardService cardService;

    private Long id;

    private List<Long> deckOfCardsId;

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

    public List<Long> getDeckOfCardsId() {
        return deckOfCardsId;
    }

    public void setDeckOfCardsId(List<Long> deckOfCardsId) {
        this.deckOfCardsId = deckOfCardsId;
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

    public DeckDTO mapToDeckDTO(Deck deck) {
        DeckDTO deckDTO = new DeckDTO();
        deckDTO.setId(deck.getId());
        deckDTO.setDeckOfCardsId(deck.getDeck().stream().map(Card::getId).collect(Collectors.toList()));
        deckDTO.setDeckName(deck.getDeckName());
        deckDTO.setSharedAccess(deck.getSharedAccess());
        deckDTO.setCover(deck.getCover());
        deckDTO.setCreationDate(deck.getCreationDate().toString());
        deckDTO.setAuthorId(deck.getAuthor().getId());
        return deckDTO;
    }

    public Deck mapToDeck(DeckDTO deckDTO) {
        String name = deckDTO.getDeckName();
        Deck deck = new Deck(name);
        deck.setDeck(deckDTO.getDeckOfCardsId().stream().map(cardService::getCardById).collect(Collectors.toList()));
        deck.setSharedAccess(deckDTO.getSharedAccess());
        deck.setCover(deckDTO.getCover());
        return deck;
    }


}
