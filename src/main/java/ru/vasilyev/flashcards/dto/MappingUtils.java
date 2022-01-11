package ru.vasilyev.flashcards.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.service.CardService;
import ru.vasilyev.flashcards.service.UserService;

import java.time.Instant;
import java.util.stream.Collectors;

@Component
public class MappingUtils {

    @Autowired
    UserService userService;

    @Autowired
    CardService cardService;

    public CardDTO mapToCardDTO(Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(card.getId());

        if(card.getImage() != null) {
            cardDTO.setImage(card.getImage());
        }

        cardDTO.setWord(card.getWord());
        cardDTO.setTranslatedWord(card.getTranslatedWord());

        if(card.getExampleOfUse() != null) {
            cardDTO.setExampleOfUse(card.getExampleOfUse());
        }

        cardDTO.setAuthorId(card.getAuthor().getId());

        if(card.getExampleOfPronunciation() != null) {
            cardDTO.setExampleOfPronunciation(card.getExampleOfPronunciation());
        }

        cardDTO.setCreationDate(card.getCreationDate().toString());
        return cardDTO;
    }

    public Card mapToCard(CardDTO cardDTO){
        String word = cardDTO.getWord();
        Card card = new Card(word);

        if(cardDTO.getTranslatedWord() != null) {
            card.setTranslatedWord(cardDTO.getTranslatedWord());
        }

        if(cardDTO.getImage() != null) {
            card.setImage(cardDTO.getImage());
        }

        if(cardDTO.getExampleOfUse() != null) {
            card.setExampleOfUse(cardDTO.getExampleOfUse());
        }

        if(cardDTO.getExampleOfPronunciation() != null) {
            card.setExampleOfPronunciation(cardDTO.getExampleOfPronunciation());
        }

        return card;
    }

    public DeckDTO mapToDeckDTO(Deck deck) {
        DeckDTO deckDTO = new DeckDTO();
        deckDTO.setId(deck.getId());

        if(deck.getDeck() != null) {
            deckDTO.setDeckOfCardsId(deck.getDeck().stream().map(Card::getId).collect(Collectors.toList()));
        }

        deckDTO.setDeckName(deck.getDeckName());

        if(deck.getSharedAccess() != null) {
            deckDTO.setSharedAccess(deck.getSharedAccess());
        }

        if(deck.getCover() != null) {
            deckDTO.setCover(deck.getCover());
        }

        if(deck.getCreationDate() != null) {
            deckDTO.setCreationDate(deck.getCreationDate().toString());
        }

        if(deck.getAuthor() != null) {
            deckDTO.setAuthorId(deck.getAuthor().getId());
        }

        return deckDTO;
    }

    public Deck mapToDeck(DeckDTO deckDTO) {
        String name = deckDTO.getDeckName();
        Deck deck = new Deck(name);

        if(deckDTO.getDeckOfCardsId() != null) {
            deck.setDeck(deckDTO.getDeckOfCardsId().stream().map(cardService::getCardById).collect(Collectors.toList()));
        }

        if(deckDTO.getSharedAccess() != null) {
            deck.setSharedAccess(deckDTO.getSharedAccess());
        }

        if(deckDTO.getCover() != null) {
            deck.setCover(deckDTO.getCover());
        }

        if(deckDTO.getAuthorId() != null) {
            deck.setAuthor(userService.getUserById(deckDTO.getAuthorId()));
        }

        if(deckDTO.getCreationDate() != null) {
            deck.setCreationDate(Instant.parse(deckDTO.getCreationDate()));
        }

        return deck;
    }

    public StatisticsDTO mapToStatisticsDTO(Statistics statistics) {
        StatisticsDTO statisticsDTO = new StatisticsDTO();

        statisticsDTO.setId(statistics.getId());

        if(statistics.getUser() != null) {
            statisticsDTO.setUserId(statistics.getUser().getId());
        }

        if(statistics.getCard() != null) {
            statisticsDTO.setCardId(statistics.getCard().getId());
        }

        if(statistics.getLastRecurrenceDate() != null) {
            statisticsDTO.setLastRecurrenceDate(statistics.getLastRecurrenceDate().toString());
        }

        statisticsDTO.setKnowledgeLevel(statistics.getKnowledgeLevel());

        if(statistics.getHistory() != null) {
            statisticsDTO.setHistory(statistics.getHistory());
        }

        return statisticsDTO;
    }

    public Statistics mapToStatistics(StatisticsDTO statisticsDTO) {
        Statistics statistics = new Statistics(statisticsDTO.getKnowledgeLevel());

        if(statisticsDTO.getCardId() != null) {
            statistics.setCard(cardService.getCardById(statisticsDTO.getCardId()));
        }

        if(statisticsDTO.getUserId() != null) {
            statistics.setUser(userService.getUserById(statisticsDTO.getUserId()));
        }

        if(statisticsDTO.getLastRecurrenceDate() != null) {
            statistics.setLastRecurrenceDate(Instant.parse(statisticsDTO.getLastRecurrenceDate()));
        }

        if(statisticsDTO.getHistory() != null) {
            statistics.setHistory(statisticsDTO.getHistory());
        }

        return  statistics;
    }
}
