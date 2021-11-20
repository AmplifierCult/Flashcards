package ru.vasilyev.flashcards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.CardRepository;
import ru.vasilyev.flashcards.repository.DeckRepository;
import ru.vasilyev.flashcards.repository.StatisticsRepository;
import ru.vasilyev.flashcards.repository.UserRepository;

@Component
public class LoadDataBase {

    private static final Logger log = LoggerFactory.getLogger(LoadDataBase.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    StatisticsRepository statisticsRepository;

    @Autowired
    DeckRepository deckRepository;

    void initUserDatabase() {
        log.info("Preloading " + userRepository.save(new User("Andrey")));
        log.info("Preloading " + userRepository.save(new User("Ivan")));
        log.info("Preloading " + userRepository.save(new User("Fedor")));
        log.info("Preloading " + userRepository.save(new User("Igor")));
    }

    void cleanUserDataBase() {
        userRepository.deleteAll();
    }


    void initCardDatabase() {
        log.info("Preloading " + cardRepository.save(new Card("Wood")));
        log.info("Preloading " + cardRepository.save(new Card("Iron")));
        log.info("Preloading " + cardRepository.save(new Card("Water")));
        log.info("Preloading " + cardRepository.save(new Card("Rock")));
    }

    void cleanCardDataBase() {
        cardRepository.deleteAll();
    }

    void initStatisticsDatabase() {
        log.info("Preloading " + statisticsRepository.save(new Statistics("Low")));
        log.info("Preloading " + statisticsRepository.save(new Statistics("Middle")));
        log.info("Preloading " + statisticsRepository.save(new Statistics("High")));
        log.info("Preloading " + statisticsRepository.save(new Statistics("Perfect")));
    }

    void cleanStatisticsDataBase() {
        statisticsRepository.deleteAll();
    }

    void initDeckDatabase() {
        log.info("Preloading " + deckRepository.save(new Deck("Metals")));
        log.info("Preloading " + deckRepository.save(new Deck("Weather")));
        log.info("Preloading " + deckRepository.save(new Deck("Types of wood")));
        log.info("Preloading " + deckRepository.save(new Deck("Types of animals")));
    }

    void cleanDeckDataBase() {
        deckRepository.deleteAll();
    }
}
