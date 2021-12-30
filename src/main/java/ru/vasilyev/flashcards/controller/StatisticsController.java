package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.dto.MappingUtils;
import ru.vasilyev.flashcards.dto.StatisticsDTO;
import ru.vasilyev.flashcards.service.StatisticsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StatisticsController {

    private static final Logger log = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    MappingUtils statisticsDTOMapper;

    @GetMapping("/statistics")
    List<StatisticsDTO> all() {
        return statisticsService.getAllStatistics().stream().map(statisticsDTOMapper::mapToStatisticsDTO).collect(Collectors.toList());
    }

    @GetMapping("/statistics/{id}")
    StatisticsDTO getStatisticsById(@PathVariable Long id) {
        return statisticsDTOMapper.mapToStatisticsDTO(statisticsService.getStatisticsById(id));
    }

    @GetMapping("/statistics/search")
    List<StatisticsDTO> getStatisticsByKnowledgeLevel(@RequestParam(value = "knowledgeLevel", required = false) String knowledgeLevel) {
        return statisticsService.getStatisticsByKnowledgeLevel(knowledgeLevel).stream().map(statisticsDTOMapper::mapToStatisticsDTO).collect(Collectors.toList());
    }

    @PostMapping("/statistics")
    @ResponseStatus(HttpStatus.CREATED)
    StatisticsDTO createStatistics(@RequestBody StatisticsDTO newStatisticsDTO) {
        Statistics newStatistics = statisticsDTOMapper.mapToStatistics(newStatisticsDTO);
        return statisticsDTOMapper.mapToStatisticsDTO(statisticsService.createStatistics(newStatistics));
    }

    @PutMapping("/statistics/{id}")
    StatisticsDTO replaceStatistics(@RequestBody StatisticsDTO newStatisticsDTO, @PathVariable Long id) {
        Statistics newStatistics = statisticsDTOMapper.mapToStatistics(newStatisticsDTO);
        return statisticsDTOMapper.mapToStatisticsDTO(statisticsService.replaceStatistics(newStatistics, id));
    }

    @DeleteMapping("/statistics/{id}")
    void deleteStatistics(@PathVariable Long id) {
        statisticsService.deleteStatistics(id);
    }
}
