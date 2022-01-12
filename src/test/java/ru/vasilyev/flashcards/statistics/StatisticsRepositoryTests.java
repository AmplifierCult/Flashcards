package ru.vasilyev.flashcards.statistics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vasilyev.flashcards.LoadDataBase;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.CardRepository;
import ru.vasilyev.flashcards.repository.StatisticsRepository;
import ru.vasilyev.flashcards.repository.UserRepository;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StatisticsRepositoryTests {
    @Autowired
    StatisticsRepository statisticsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    LoadDataBase loadDataBase;

    @BeforeEach
    void setUp() {
        loadDataBase.initUserDatabase();
        loadDataBase.initCardDatabase();
        loadDataBase.initStatisticsDatabase();
    }

    @AfterEach
    void tearDown() {
        loadDataBase.cleanStatisticsDataBase();
        loadDataBase.cleanCardDataBase();
        loadDataBase.cleanUserDataBase();
    }

    @Test
    void baseCreateOperations() {
        User author = userRepository.findByLogin("Andrey");

        Card newCard = new Card("Gold");
        newCard.setAuthor(author);
        newCard.setCreationDate(Instant.now());
        newCard.setTranslatedWord("Золото");
        cardRepository.save(newCard);

        Statistics newStatistics = new Statistics("High");
        newStatistics.setUser(author);
        newStatistics.setCard(newCard);

        statisticsRepository.save(newStatistics);
        assertEquals(5, statisticsRepository.count());
        assertEquals(newStatistics.getId(), statisticsRepository.findById(newStatistics.getId()).get().getId());

    }

    @Test
    void baseUpdateOperations() {
        Statistics statisticsForUpdate = statisticsRepository.findByKnowledgeLevel("Low").get(0);
        statisticsForUpdate.setKnowledgeLevel("Perfect");
        statisticsRepository.save(statisticsForUpdate);
        assertEquals(statisticsForUpdate.getKnowledgeLevel(), statisticsRepository.findById(statisticsForUpdate.getId()).get().getKnowledgeLevel());
    }

    @Test
    void baseDeleteOperations() {
        Statistics statisticsForDelete = statisticsRepository.findByKnowledgeLevel("Low").get(0);
        statisticsRepository.delete(statisticsForDelete);
        assertEquals(3, statisticsRepository.count());
    }
}
