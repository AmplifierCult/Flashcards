package ru.vasilyev.flashcards.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.dto.DeckDTO;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Mapper(componentModel = "spring", uses = {CardMapper.class})
public interface DeckMapper {

    @Mapping(target = "deckDTO", source = "deck")
    DeckDTO mapToDeckDTO(Deck deck);

    default String creationDateToString(Instant creationDate) {
        if (creationDate != null) {
            return DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss Z", Locale.US).withZone(ZoneId.systemDefault()).format(creationDate);
        } else return null;
    }

    default Instant stringToCreationDate(String creationDate) {
        return null;
    }

    @InheritInverseConfiguration
    Deck mapToDeck(DeckDTO deckDTO);
}
