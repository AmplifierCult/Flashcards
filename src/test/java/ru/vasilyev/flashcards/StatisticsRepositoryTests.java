package ru.vasilyev.flashcards;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.repository.StatisticsRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StatisticsRepositoryTests {
    @Autowired
    StatisticsRepository repository;

    @Test
    void baseCRUDOperations() {
        Statistics savedStatistics;
        savedStatistics = repository.save(new Statistics("Low"));
        assertEquals(1, repository.count());
        assertEquals(savedStatistics.getKnowledgeLevel(), repository.findByKnowledgeLevel("Low").getKnowledgeLevel());
        assertEquals(savedStatistics.getId(), repository.findById(savedStatistics.getId()).get().getId());

        savedStatistics.setKnowledgeLevel("Perfect");
        repository.save(savedStatistics);
        assertEquals(savedStatistics.getId(), repository.findByKnowledgeLevel("Perfect").getId());

        repository.delete(savedStatistics);
        assertEquals(0, repository.count());
    }
}
