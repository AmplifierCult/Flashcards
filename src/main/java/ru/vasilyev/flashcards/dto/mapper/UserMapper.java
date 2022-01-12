package ru.vasilyev.flashcards.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.dto.UserDTO;

@Mapper(componentModel = "spring",uses = {DeckMapper.class})
public interface UserMapper {

    @Mapping(target = "decksDTO", source = "decks")
    UserDTO mapToUserDTO(User user);

    @InheritInverseConfiguration
    User mapToUser(UserDTO userDTO);
}
