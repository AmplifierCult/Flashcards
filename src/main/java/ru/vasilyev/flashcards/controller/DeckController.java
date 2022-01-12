package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.dto.DeckDTO;
import ru.vasilyev.flashcards.dto.MappingUtils;
import ru.vasilyev.flashcards.dto.mapper.DeckMapper;
import ru.vasilyev.flashcards.service.DeckService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DeckController {

    private static final Logger log = LoggerFactory.getLogger(DeckController.class);

    @Autowired
    DeckService deckService;

    @Autowired
    DeckMapper deckMapper;

    @GetMapping("/decks")
    List<DeckDTO> all() {
        return deckService.getAllDecks().stream().map(deckMapper::mapToDeckDTO).collect(Collectors.toList());
    }

    @GetMapping("/decks/{id}")
    DeckDTO getDeckById(@PathVariable Long id) {
        return deckMapper.mapToDeckDTO(deckService.getDeckById(id));
    }

    @GetMapping("/decks/search")
    DeckDTO getDeckByDeckName(@RequestParam(value = "deckName", required = false) String deckName) {
        return deckMapper.mapToDeckDTO(deckService.getDeckByDeckName(deckName));
    }

    @PostMapping("/decks")
    @ResponseStatus(HttpStatus.CREATED)
    DeckDTO createDeck(@RequestBody DeckDTO newDeckDTO) {
        Deck newDeck = deckMapper.mapToDeck(newDeckDTO);
        return deckMapper.mapToDeckDTO(deckService.createDeck(newDeck));
    }

    @PutMapping("/decks/{id}")
    DeckDTO replaceDeck(@RequestBody DeckDTO newDeckDTO, @PathVariable Long id) {
        Deck replacedDeck = deckMapper.mapToDeck(newDeckDTO);
        return deckMapper.mapToDeckDTO(deckService.replaceDeck(replacedDeck, id));
    }

    @DeleteMapping("/decks/{id}")
    void deleteDeck(@PathVariable Long id) {
        deckService.deleteDeck(id);
    }
}
