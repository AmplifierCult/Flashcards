package ru.vasilyev.flashcards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.CardRepository;
import ru.vasilyev.flashcards.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    UserRepository userRepository;

    public List<Card> getAllCards(){
        return cardRepository.findAll();
    }

    public Card getCardById(Long id){
        return cardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Card not found."));
    }

    public Card getCardByWord(String word){
        return cardRepository.findByWord(word);
    }

    public Card createCard(Card newCard) {
        newCard.setAuthor(assignAuthor());
        newCard.setCreationDate(Instant.now());
        return cardRepository.save(newCard);
    }

    private User assignAuthor() {
        User user = new User("Mick");
        user.setPassword("qwerty");
        user.setRegistrationDate(Instant.now());
        return userRepository.save(user);
    }

    public Card replaceCard(Card newCard, Long id) {
        newCard.setCreationDate(Instant.now());
        cardRepository.findById(id)
                .map(card -> {
                    card.setImage(newCard.getImage());
                    card.setWord(newCard.getWord());
                    card.setTranslatedWord(newCard.getTranslatedWord());
                    card.setExampleOfPronunciation(newCard.getExampleOfPronunciation());
                    card.setExampleOfUse(newCard.getExampleOfUse());
                    card.setCreationDate(newCard.getCreationDate());
                    return cardRepository.save(card);
                })
                .orElseGet(() -> {
                    newCard.setId(id);
                    return cardRepository.save(newCard);
                });
        return getCardById(id);
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
