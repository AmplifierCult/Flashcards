package ru.vasilyev.flashcards.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.repository.CardRepository;

import java.time.Instant;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    public Card createCard(Card newCard) {
        validateCard(newCard);
        newCard.setCreationDate(Instant.now());
        return cardRepository.save(newCard);
    }

    private void validateCard(Card card) {
    }


}
