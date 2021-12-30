package ru.vasilyev.flashcards.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.dto.CardDTO;

//@Mapper
public interface CardMapper {

    CardMapper MAPPER = Mappers.getMapper(CardMapper.class);


    CardDTO mapToCardDTO(Card card);

    Card mapToCard(CardDTO cardDTO);
}
