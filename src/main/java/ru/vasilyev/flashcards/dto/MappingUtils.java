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

}
