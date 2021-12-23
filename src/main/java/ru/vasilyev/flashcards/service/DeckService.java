package ru.vasilyev.flashcards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.DeckRepository;
import ru.vasilyev.flashcards.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
public class DeckService {

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    UserRepository userRepository;

    public List<Deck> getAllDecks() {
        return deckRepository.findAll();
    }

    public Deck getDeckById(Long id) {
        validateId(id);
        return deckRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Deck not found."));
    }

    public Deck getDeckByDeckName(String deckName) {
        validateDeckName(deckName);
        return deckRepository.findByDeckName(deckName);
    }

    private void validateDeckName(String deckName) {
        if(ObjectUtils.isEmpty(deckName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 400. User did not enter a deck name.");
        } else if(ObjectUtils.isEmpty(deckRepository.findByDeckName(deckName))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Deck not found.");
        }
    }

    public Deck createDeck(Deck newDeck) {
        validateDeck(newDeck);
        newDeck.setAuthor(assignAuthor());
        return deckRepository.save(newDeck);
    }

    public Deck replaceDeck(Deck newDeck, Long id) {
        validateId(id);
        validateDeck(newDeck);
        return deckRepository.findById(id)
                .map(deck -> {
                    deck.setDeck(newDeck.getDeck());
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

    private void validateDeck(Deck newDeck) { //TODO реализовать
    }

    public void deleteDeck(Long id) {
        validateId(id);
        deckRepository.deleteById(id);
    }

    private void validateId(Long id) { //TODO реализовать
    }

    private User assignAuthor() {
        User user = new User("Mick");
        user.setPassword("qwerty");
        user.setRegistrationDate(Instant.now());
        return userRepository.save(user);
    }

}
