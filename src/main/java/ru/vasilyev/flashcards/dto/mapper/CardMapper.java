package ru.vasilyev.flashcards.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.dto.CardDTO;
import ru.vasilyev.flashcards.service.UserService;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(target = "authorId", source = "author")
    @Mapping(target = "creationDate", dateFormat = "EEE, d MMM yyyy HH:mm:ss Z")
    CardDTO mapToCardDTO(Card card);

    default Long authorToAuthorId(User author) {
        if (author != null) {
            return author.getId();
        } else return null;
    }

    @InheritInverseConfiguration
    Card mapToCard(CardDTO cardDTO);

    default User authorIdToAuthor(Long authorId) {
        return null;
    }
}
