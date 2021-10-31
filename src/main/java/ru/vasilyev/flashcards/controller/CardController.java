package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.LoadDataBase;
import ru.vasilyev.flashcards.repository.CardRepository;

import java.util.List;

@RestController
public class CardController {

    private static final Logger log = LoggerFactory.getLogger(LoadDataBase.class);

    private final CardRepository repository;

    public CardController(CardRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/cards")
    List<Card> all() {
        return repository.findAll();
    }

    @GetMapping("/cards/{id}")
    Card getCardById(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Card not found."));
    }

    @GetMapping("/cards/search")
    Card getCardByWord(@RequestParam(value = "word", required = false) String word) {
        if(ObjectUtils.isEmpty(word)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 400. User don't write a word.");
        } else if(ObjectUtils.isEmpty(repository.findByWord(word))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Card not found.");
        } else return repository.findByWord(word);
    }

    @PostMapping("/cards")
    Card newCard(@RequestBody Card newCard) {
        return repository.save(newCard);
    }

    @PutMapping("/cards/{id}")
    Card replaceCard(@RequestBody Card newCard, @PathVariable Long id) {
        return repository.findById(id)
                .map(card -> {
                    card.setImage(newCard.getImage());
                    card.setWord(newCard.getWord());
                    card.setTranslatedWord(newCard.getTranslatedWord());
                    card.setExampleOfPronunciation(newCard.getExampleOfPronunciation());
                    card.setExampleOfUse(newCard.getExampleOfUse());
                    card.setAuthor(newCard.getAuthor());
                    return repository.save(card);
                })
                .orElseGet(() -> {
                    newCard.setId(id);
                    return repository.save(newCard);
                });
    }

    @DeleteMapping("/cards/{id}")
    void deleteCard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
