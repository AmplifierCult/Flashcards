package ru.vasilyev.flashcards.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.dto.UserDTO;
import ru.vasilyev.flashcards.service.DeckService;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "registrationDate", dateFormat = "EEE, d MMM yyyy HH:mm:ss Z")
    @Mapping(target = "lastActionDate", dateFormat = "EEE, d MMM yyyy HH:mm:ss Z")
    @Mapping(target = "deckNames", source = "decks")
    UserDTO mapToUserDTO(User user);

    default List<String> decksToDeckNames(List<Deck> decks) {
        if (decks != null) {
            return decks.stream().map(Deck::getDeckName).collect(Collectors.toList());
        } else return null;
    }

    default List<Deck> deckNamesToDecks(List<String> deckNames) {
        if (deckNames != null) {
            DeckService deckService = new DeckService();
            return deckNames.stream().map(deckService::getDeckByDeckName).collect(Collectors.toList());
        } else return null;
    }

    @InheritInverseConfiguration
    User mapToUser(UserDTO userDTO);
}
