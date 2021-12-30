package ru.vasilyev.flashcards.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.dto.StatisticsDTO;

//@Mapper
public interface StatisticsMapper {

    StatisticsMapper MAPPER = Mappers.getMapper(StatisticsMapper.class);

    StatisticsDTO mapToStatisticsDTO(Statistics statistics);

    Statistics mapToStatistics(StatisticsDTO statisticsDTO);
}
