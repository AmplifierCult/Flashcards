package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vasilyev.flashcards.dto.StatisticsDTO;
import ru.vasilyev.flashcards.dto.mapper.StatisticsMapper;
import ru.vasilyev.flashcards.service.StatisticsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StatisticsController {

    private static final Logger log = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    StatisticsMapper statisticsMapper;

    @GetMapping("/statistics")
    List<StatisticsDTO> all() {
        return statisticsService.getAllStatistics().stream().map(statisticsMapper::mapToStatisticsDTO).collect(Collectors.toList());
    }

    @GetMapping("/statistics/{id}")
    StatisticsDTO getStatisticsById(@PathVariable Long id) {
        return statisticsMapper.mapToStatisticsDTO(statisticsService.getStatisticsById(id));
    }

    @GetMapping("/statistics/search")
    List<StatisticsDTO> getStatisticsByKnowledgeLevel(@RequestParam(value = "knowledgeLevel", required = false) String knowledgeLevel) {
        return statisticsService.getStatisticsByKnowledgeLevel(knowledgeLevel).stream().map(statisticsMapper::mapToStatisticsDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/statistics/{id}")
    void deleteStatistics(@PathVariable Long id) {
        statisticsService.deleteStatistics(id);
    }
}
