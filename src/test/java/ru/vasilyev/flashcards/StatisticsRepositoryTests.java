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

        //create
        savedStatistics = repository.save(new Statistics("Low"));
        assertEquals(5, repository.count());
        assertEquals(savedStatistics.getId(), repository.findById(savedStatistics.getId()).get().getId());

        //update
        savedStatistics.setKnowledgeLevel("Perfect");
        repository.save(savedStatistics);
        assertEquals(savedStatistics.getKnowledgeLevel(), repository.findById(savedStatistics.getId()).get().getKnowledgeLevel());

        //delete
        repository.delete(savedStatistics);
        assertEquals(4, repository.count());
    }
}
