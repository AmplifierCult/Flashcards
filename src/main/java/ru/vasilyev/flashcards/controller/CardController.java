package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.dto.CardDTO;
import ru.vasilyev.flashcards.dto.mapper.CardMapper;
import ru.vasilyev.flashcards.service.CardService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CardController {

    private static final Logger log = LoggerFactory.getLogger(CardController.class);

    @Autowired
    CardService cardService;

    @Autowired
    CardMapper cardMapper;

    @GetMapping("/cards")
    List<CardDTO> all() {
        return cardService.getAllCards().stream().map(cardMapper::mapToCardDTO).collect(Collectors.toList());
    }

    @GetMapping("/cards/{id}")
    CardDTO getCardById(@PathVariable Long id) {
        return cardMapper.mapToCardDTO(cardService.getCardById(id));
    }

    @GetMapping("/cards/search")
    CardDTO getCardByWord(@RequestParam(value = "word", required = false) String word) {
        return cardMapper.mapToCardDTO(cardService.getCardByWord(word));
    }

    @PostMapping("/cards")
    @ResponseStatus(HttpStatus.CREATED)
    CardDTO createCard(@RequestBody CardDTO newCardDTO) {
        Card newCard = cardMapper.mapToCard(newCardDTO);
        return cardMapper.mapToCardDTO(cardService.createCard(newCard));
    }

    @PutMapping("/cards/{id}")
    CardDTO replaceCard(@RequestBody CardDTO newCardDTO, @PathVariable Long id) {
        Card newCard = cardMapper.mapToCard(newCardDTO);
        return cardMapper.mapToCardDTO(cardService.replaceCard(newCard, id));
    }

    @DeleteMapping("/cards/{id}")
    void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }
}
