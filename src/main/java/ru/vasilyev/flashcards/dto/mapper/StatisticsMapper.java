package ru.vasilyev.flashcards.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.dto.StatisticsDTO;

import java.time.Instant;

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



    @InheritInverseConfiguration
    Statistics mapToStatistics(StatisticsDTO statisticsDTO);

    default User UserIdToUser(Long userId) {
        return null;
    }

    default Card CardIdToCard(Long cardId) {
        return null;
    }

    default Instant stringToLastRecurrenceDate(String lastRecurrenceDate) {
        return null;
    }
}
