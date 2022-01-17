package ru.vasilyev.flashcards.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.DeckRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
public class DeckService {

    private static final Logger log = LoggerFactory.getLogger(DeckService.class);

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    UserService userService;

    public List<Deck> getAllDecks() {
        return deckRepository.findAll();
    }

    public Deck getDeckById(@Positive Long id) {
        return deckRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Deck not found."));
    }

    public Deck getDeckByDeckName(@NotBlank(message = "deckName cannot be empty") String deckName) {
        return deckRepository.findByDeckName(deckName);
    }

    public List<Deck> getDecksByAuthor(Long authorId) {
        User author = userService.getUserById(authorId);
        return deckRepository.findByAuthor(author);
    }

    public Deck createDeck(Deck newDeck) {
        newDeck.setAuthor(assignAuthor());
        newDeck.setCreationDate(Instant.now());
        validateDeck(newDeck);
        return deckRepository.save(newDeck);
    }

    public Deck replaceDeck(Deck newDeck, @Positive Long id) {
        validateDeck(newDeck);
        return deckRepository.findById(id)
                .map(deck -> {
                    deck.setCards(newDeck.getCards());
                    deck.setDeckName(newDeck.getDeckName());
                    deck.setSharedAccess(newDeck.getSharedAccess());
                    deck.setCover(newDeck.getCover());
                    deck.setCreationDate(newDeck.getCreationDate());
                    deck.setAuthor(newDeck.getAuthor());
                    return deckRepository.save(deck);
                })
                .orElseGet(() -> {
                    newDeck.setId(id);
                    return deckRepository.save(newDeck);
                });
    }

    private void validateDeck(Deck deck) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Deck>> violations = validator.validate(deck);

        for (ConstraintViolation<Deck> violation : violations) {
            log.error(violation.getMessage());
        }
    }

    public void deleteDeck(@Positive Long id) {
        deckRepository.deleteById(id);
    }

    private User assignAuthor() { //TODO Заглушка до настройки Spring Security
        User user = new User("Mick");
        user.setPassword("qwerty");
        user.setRegistrationDate(Instant.now());
        return userService.createUser(user);
    }
}
