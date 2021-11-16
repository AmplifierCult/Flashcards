package ru.vasilyev.flashcards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.repository.StatisticsRepository;

import java.util.List;

@RestController
public class StatisticsController {

    private static final Logger log = LoggerFactory.getLogger(StatisticsController.class);

    private final StatisticsRepository repository;

    public StatisticsController(StatisticsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/statistics")
    List<Statistics> all() {
        return repository.findAll();
    }

    @GetMapping("/statistics/{id}")
    Statistics getStatisticsById(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Statistics not found."));
    }

    @GetMapping("/statistics/search")
    List<Statistics> getStatisticsByKnowledgeLevel(@RequestParam(value = "knowledgeLevel", required = false) String knowledgeLevel) {
        if(ObjectUtils.isEmpty(knowledgeLevel)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 400. User did not enter a knowledge level.");
        } else if(ObjectUtils.isEmpty(repository.findByKnowledgeLevel(knowledgeLevel))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Statistics not found.");
        } else return repository.findByKnowledgeLevel(knowledgeLevel);
    }

    @PostMapping("/statistics")
    @ResponseStatus(HttpStatus.CREATED)
    Statistics newStatistics(@RequestBody Statistics newStatistics) {
        return repository.save(newStatistics);
    }

    @PutMapping("/statistics/{id}")
    Statistics replaceStatistics(@RequestBody Statistics newStatistics, @PathVariable Long id) {
        return repository.findById(id)
                .map(statistics -> {
                    statistics.setUser(newStatistics.getUser());
                    statistics.setCard(newStatistics.getCard());
                    statistics.setLastRecurrenceDate(newStatistics.getLastRecurrenceDate());
                    statistics.setKnowledgeLevel(newStatistics.getKnowledgeLevel());
                    statistics.setHistory(newStatistics.getHistory());
                    return repository.save(statistics);
                })
                .orElseGet(() -> {
                    newStatistics.setId(id);
                    return repository.save(newStatistics);
                });
    }

    @DeleteMapping("/statistics/{id}")
    void deleteStatistics(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
