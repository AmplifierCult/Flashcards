package ru.vasilyev.flashcards.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.StatisticsRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

@Service
public class StatisticsService {

    private static final Logger log = LoggerFactory.getLogger(StatisticsService.class);

    @Autowired
    StatisticsRepository statisticsRepository;

    @Autowired
    UserService userService;

    @Autowired
    CardService cardService;

    public List<Statistics> getAllStatistics() {
        return statisticsRepository.findAll();
    }

    public Statistics getStatisticsById(@Positive Long id) {
        return statisticsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: 404. Statistics not found."));
    }

    public List<Statistics> getStatisticsByKnowledgeLevel(@NotBlank(message = "knowledgeLevel cannot be empty") String knowledgeLevel) {
        return statisticsRepository.findByKnowledgeLevel(knowledgeLevel);
    }

    public Statistics createStatistics(Statistics newStatistics) {
        newStatistics.setUser(assignUser());
        newStatistics.setCard(assignCard());
        validateStatistics(newStatistics);
        return statisticsRepository.save(newStatistics);
    }

    private User assignUser() {
        return userService.getUserByLogin("Fedor");
    }

    private Card assignCard() {
        return cardService.getCardByWord("Iron");
    }

    public Statistics replaceStatistics(Statistics newStatistics, @Positive Long id) {
        validateStatistics(newStatistics);
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

    private void validateStatistics(Statistics statistics) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Statistics>> violations = validator.validate(statistics);

        for (ConstraintViolation<Statistics> violation : violations) {
            log.error(violation.getMessage());
        }
    }

    public void deleteStatistics(@Positive Long id) {
        statisticsRepository.deleteById(id);
    }


}
