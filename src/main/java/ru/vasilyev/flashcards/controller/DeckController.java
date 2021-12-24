package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.dto.DeckDTO;
import ru.vasilyev.flashcards.service.DeckService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DeckController {

    private static final Logger log = LoggerFactory.getLogger(DeckController.class);

    @Autowired
    DeckService deckService;

    @Autowired
    DeckDTO deckDTOMapper;

    @GetMapping("/decks")
    List<DeckDTO> all() {
        return deckService.getAllDecks().stream().map(deckDTOMapper::mapToDeckDTO).collect(Collectors.toList());
    }

    @GetMapping("/decks/{id}")
    DeckDTO getDeckById(@PathVariable Long id) {
        return deckDTOMapper.mapToDeckDTO(deckService.getDeckById(id));
    }

    @GetMapping("/decks/search")
    DeckDTO getDeckByDeckName(@RequestParam(value = "deckName", required = false) String deckName) {
        return deckDTOMapper.mapToDeckDTO(deckService.getDeckByDeckName(deckName));
    }

    @PostMapping("/decks")
    @ResponseStatus(HttpStatus.CREATED)
    DeckDTO createDeck(@RequestBody DeckDTO newDeckDTO) {
        Deck newDeck = deckDTOMapper.mapToDeck(newDeckDTO);
        return deckDTOMapper.mapToDeckDTO(deckService.createDeck(newDeck));
    }

    @PutMapping("/decks/{id}")
    DeckDTO replaceDeck(@RequestBody DeckDTO newDeckDTO, @PathVariable Long id) {
        Deck replacedDeck = deckService.replaceDeck(deckDTOMapper.mapToDeck(newDeckDTO), id);
        return deckDTOMapper.mapToDeckDTO(replacedDeck);
    }

    @DeleteMapping("/decks/{id}")
    void deleteDeck(@PathVariable Long id) {
        deckService.deleteDeck(id);
    }
}
