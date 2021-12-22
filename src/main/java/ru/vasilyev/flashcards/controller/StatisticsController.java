package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.repository.StatisticsRepository;
import ru.vasilyev.flashcards.service.StatisticsService;

import java.util.List;

@RestController
public class StatisticsController {

    private static final Logger log = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    StatisticsService statisticsService;

    @GetMapping("/statistics")
    List<Statistics> all() {
        return statisticsService.getAllStatistics();
    }

    @GetMapping("/statistics/{id}")
    Statistics getStatisticsById(@PathVariable Long id) {
        return statisticsService.getStatisticsById(id);
    }

    @GetMapping("/statistics/search")
    List<Statistics> getStatisticsByKnowledgeLevel(@RequestParam(value = "knowledgeLevel", required = false) String knowledgeLevel) {
        return statisticsService.getStatisticsByKnowledgeLevel(knowledgeLevel);
    }

    @PostMapping("/statistics")
    @ResponseStatus(HttpStatus.CREATED)
    Statistics createStatistics(@RequestBody Statistics newStatistics) {
        return statisticsService.createStatistics(newStatistics);
    }

    @PutMapping("/statistics/{id}")
    Statistics replaceStatistics(@RequestBody Statistics newStatistics, @PathVariable Long id) {
        return statisticsService.replaceStatistics(newStatistics, id);
    }

    @DeleteMapping("/statistics/{id}")
    void deleteStatistics(@PathVariable Long id) {
        statisticsService.deleteStatistics(id);
    }
}
