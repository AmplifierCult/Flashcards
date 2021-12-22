package ru.vasilyev.flashcards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.repository.CardRepository;

import java.time.Instant;
import java.util.List;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    public List<Card> getAllCards(){
        return cardRepository.findAll();
    }

    public Card getCardById(Long id){
        validateId(id);
        return cardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Card not found."));
    }

    private void validateId(Long id) { //TODO реализовать
    }

    public Card getCardByWord(String word){
        validateWord(word);
        if(ObjectUtils.isEmpty(word)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 400. User did not enter a word.");
        } else if(ObjectUtils.isEmpty(cardRepository.findByWord(word))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Card not found.");
        } else return cardRepository.findByWord(word);
    }

    private void validateWord(String word) { //TODO реализовать

    }

    public Card createCard(Card newCard) {
        validateCard(newCard);
        newCard.setCreationDate(Instant.now());
        return cardRepository.save(newCard);
    }

    public Card replaceCard(Card newCard, Long id) {
        validateCard(newCard);
        return cardRepository.findById(id)
                .map(card -> {
                    card.setImage(newCard.getImage());
                    card.setWord(newCard.getWord());
                    card.setTranslatedWord(newCard.getTranslatedWord());
                    card.setExampleOfPronunciation(newCard.getExampleOfPronunciation());
                    card.setExampleOfUse(newCard.getExampleOfUse());
                    card.setAuthor(newCard.getAuthor());
                    return cardRepository.save(card);
                })
                .orElseGet(() -> {
                    newCard.setId(id);
                    return cardRepository.save(newCard);
                });
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }

    private void validateCard(Card card) { //TODO реализовать
    }
}
