package ru.vasilyev.flashcards.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.dto.DeckDTO;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DeckMapper {

    @Mapping(target = "cardsId", source = "cards")
    DeckDTO mapToDeckDTO(Deck deck);

    default List<Long> cardsToCardsId(List<Card> cards) {
        if (cards != null) {
            return cards.stream().map(Card::getId).collect(Collectors.toList());
        } else return null;
    }

    default List<DeckDTO> decksToDecksDTO(List<Deck> decks) {
        if (decks != null) {
            return decks.stream().map(this::mapToDeckDTO).collect(Collectors.toList());
        } else return null;
    }

    Deck mapToDeck(DeckDTO deckDTO);
}
