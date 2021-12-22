package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.service.DeckService;

import java.util.List;

@RestController
public class DeckController {

    private static final Logger log = LoggerFactory.getLogger(DeckController.class);

    @Autowired
    DeckService deckService;

    @GetMapping("/decks")
    List<Deck> all() {
        return deckService.getAllDecks();
    }

    @GetMapping("/decks/{id}")
    Deck getDeckById(@PathVariable Long id) {
        return deckService.getDeckById(id);
    }

    @GetMapping("/decks/search")
    Deck getDeckByDeckName(@RequestParam(value = "deckName", required = false) String deckName) {
        return deckService.getDeckByDeckName(deckName);
    }

    @PostMapping("/decks")
    @ResponseStatus(HttpStatus.CREATED)
    Deck createDeck(@RequestBody Deck newDeck) {
        return deckService.createDeck(newDeck);
    }

    @PutMapping("/decks/{id}")
    Deck replaceDeck(@RequestBody Deck newDeck, @PathVariable Long id) {
        return deckService.replaceDeck(newDeck, id);
    }

    @DeleteMapping("/decks/{id}")
    void deleteDeck(@PathVariable Long id) {
        deckService.deleteDeck(id);
    }
}
