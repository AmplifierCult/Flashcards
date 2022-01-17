package ru.vasilyev.flashcards.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.dto.StatisticsDTO;

@Mapper(componentModel = "spring")
public interface StatisticsMapper {

    @Mapping(target = "userId", source = "user")
    @Mapping(target = "cardId", source = "card")
    StatisticsDTO mapToStatisticsDTO(Statistics statistics);

    default Long userToUserId(User user) {
        if (user != null) {
            return user.getId();
        } else return null;
    }

    default Long cardToCardId(Card card) {
        if (card != null) {
            return card.getId();
        } else return null;
    }
}
