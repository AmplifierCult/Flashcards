package ru.vasilyev.flashcards.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.dto.DeckDTO;

//@Mapper
public interface DeckMapper {

    DeckMapper MAPPER = Mappers.getMapper(DeckMapper.class);

    DeckDTO mapToDeckDTO(Deck deck);

    Deck mapToDeck(DeckDTO deckDTO);
}
