package ru.vasilyev.flashcards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.repository.StatisticsRepository;

import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    StatisticsRepository statisticsRepository;

    public List<Statistics> getAllStatistics() {
        return statisticsRepository.findAll();
    }

    public Statistics getStatisticsById(Long id) {
        validateId(id);
        return statisticsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Statistics not found."));
    }



    public List<Statistics> getStatisticsByKnowledgeLevel(String knowledgeLevel) {
        validateKnowledgeLevel(knowledgeLevel);
        return statisticsRepository.findByKnowledgeLevel(knowledgeLevel);
    }

    private void validateKnowledgeLevel(String knowledgeLevel) {
        if(ObjectUtils.isEmpty(knowledgeLevel)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 400. User did not enter a knowledge level.");
        } else if(ObjectUtils.isEmpty(statisticsRepository.findByKnowledgeLevel(knowledgeLevel))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Statistics not found.");
        }
    }

    public Statistics createStatistics(Statistics newStatistics) {
        validateStatistics(newStatistics);
        return statisticsRepository.save(newStatistics);
    }

    public Statistics replaceStatistics(Statistics newStatistics, Long id) {
        validateStatistics(newStatistics);
        validateId(id);
        return statisticsRepository.findById(id)
                .map(statistics -> {
                    statistics.setUser(newStatistics.getUser());
                    statistics.setCard(newStatistics.getCard());
                    statistics.setLastRecurrenceDate(newStatistics.getLastRecurrenceDate());
                    statistics.setKnowledgeLevel(newStatistics.getKnowledgeLevel());
                    statistics.setHistory(newStatistics.getHistory());
                    return statisticsRepository.save(statistics);
                })
                .orElseGet(() -> {
                    newStatistics.setId(id);
                    return statisticsRepository.save(newStatistics);
                });
    }

    private void validateStatistics(Statistics newStatistics) { //TODO реализовать
    }

    public void deleteStatistics(Long id) {
        validateId(id);
        statisticsRepository.deleteById(id);
    }

    private void validateId(Long id) { //TODO реализовать
    }

}
