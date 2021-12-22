package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.service.CardService;

import java.util.List;

@RestController
public class CardController {

    private static final Logger log = LoggerFactory.getLogger(CardController.class);

    @Autowired
    CardService cardService;

    @GetMapping("/cards")
    List<Card> all() {
        return cardService.getAllCards();
    }

    @GetMapping("/cards/{id}")
    Card getCardById(@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    @GetMapping("/cards/search")
    Card getCardByWord(@RequestParam(value = "word", required = false) String word) {
        return cardService.getCardByWord(word);
    }

    @PostMapping("/cards")
    @ResponseStatus(HttpStatus.CREATED)
    Card createCard(@RequestBody Card newCard) {
        return cardService.createCard(newCard);
    }


    @PutMapping("/cards/{id}")
    Card replaceCard(@RequestBody Card newCard, @PathVariable Long id) {
        return cardService.replaceCard(newCard, id);
    }

    @DeleteMapping("/cards/{id}")
    void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }
}
