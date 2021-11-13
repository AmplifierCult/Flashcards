package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.repository.DeckRepository;

import java.util.List;

@RestController
public class DeckController {

    private static final Logger log = LoggerFactory.getLogger(DeckController.class);

    private final DeckRepository repository;

    public DeckController(DeckRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/decks")
    List<Deck> all() {
        return repository.findAll();
    }

    @GetMapping("/decks/{id}")
    Deck getDeckById(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Deck not found."));
    }

    @GetMapping("/decks/search")
    Deck getDeckByDeckName(@RequestParam(value = "deckName", required = false) String deckName) {
        if(ObjectUtils.isEmpty(deckName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 400. User did not enter a deck name.");
        } else if(ObjectUtils.isEmpty(repository.findByDeckName(deckName))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Deck not found.");
        } else return repository.findByDeckName(deckName);
    }

    @PostMapping("/decks")
    Deck newDeck(@RequestBody Deck newDeck) {
        return repository.save(newDeck);
    }

    @PutMapping("/decks/{id}")
    Deck replaceDeck(@RequestBody Deck newDeck, @PathVariable Long id) {
        return repository.findById(id)
                .map(deck -> {
                    deck.setDeck(newDeck.getDeck());
                    deck.setDeckName(newDeck.getDeckName());
                    deck.setSharedAccess(newDeck.getSharedAccess());
                    deck.setCover(newDeck.getCover());
                    deck.setCreationDate(newDeck.getCreationDate());
                    return repository.save(deck);
                })
                .orElseGet(() -> {
                    newDeck.setId(id);
                    return repository.save(newDeck);
                });
    }

    @DeleteMapping("/decks/{id}")
    void deleteDeck(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
