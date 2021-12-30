package ru.vasilyev.flashcards.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.CardRepository;
import ru.vasilyev.flashcards.repository.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Positive;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
public class CardService {

    private static final Logger log = LoggerFactory.getLogger(CardService.class);

    @Autowired
    CardRepository cardRepository;

    @Autowired
    UserService userService;


    public List<Card> getAllCards(){
        return cardRepository.findAll();
    }

    public Card getCardById(@Positive Long id){
        return cardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Card not found."));
    }

    public Card getCardByWord(String word){
        return cardRepository.findByWord(word);
    }

    public Card createCard(Card newCard) {
        newCard.setAuthor(assignAuthor());
        newCard.setCreationDate(Instant.now());
        validateCard(newCard);
        return cardRepository.save(newCard);
    }

    private User assignAuthor() {
        return userService.getUserByLogin("Andrey");
    }

    public Card replaceCard(Card newCard, @Positive Long id) {
        validateCard(newCard);
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

    public void deleteCard(@Positive Long id) {
        cardRepository.deleteById(id);
    }

    private void validateCard(Card card) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Card>> violations = validator.validate(card);

        for (ConstraintViolation<Card> violation : violations) {
            log.error(violation.getMessage());
        }
    }
}
