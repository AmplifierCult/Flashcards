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
        User author = userRepository.findByLogin("Andrey");
        log.info("Preloading " + cardRepository.save(new Card("Wood", author)));
        log.info("Preloading " + cardRepository.save(new Card("Iron", author)));
        log.info("Preloading " + cardRepository.save(new Card("Water", author)));
        log.info("Preloading " + cardRepository.save(new Card("Rock", author)));
    }

    void cleanCardDataBase() {
        cardRepository.deleteAll();
    }

    void initStatisticsDatabase() {
        User author = userRepository.findByLogin("Andrey");
        Card firstCard = cardRepository.findByWord("Wood");
        Card secondCard = cardRepository.findByWord("Iron");
        Card thirdCard = cardRepository.findByWord("Water");
        Card fourthCard = cardRepository.findByWord("Rock");
        log.info("Preloading " + statisticsRepository.save(new Statistics("Low", author, firstCard)));
        log.info("Preloading " + statisticsRepository.save(new Statistics("Middle", author, secondCard)));
        log.info("Preloading " + statisticsRepository.save(new Statistics("High", author, thirdCard)));
        log.info("Preloading " + statisticsRepository.save(new Statistics("Perfect", author, fourthCard)));
    }

    void cleanStatisticsDataBase() {
        statisticsRepository.deleteAll();
    }

    void initDeckDatabase() {
        User author = userRepository.findByLogin("Andrey");
        log.info("Preloading " + deckRepository.save(new Deck("Metals", author)));
        log.info("Preloading " + deckRepository.save(new Deck("Weather", author)));
        log.info("Preloading " + deckRepository.save(new Deck("Types of wood", author)));
        log.info("Preloading " + deckRepository.save(new Deck("Types of animals", author)));
    }

    void cleanDeckDataBase() {
        deckRepository.deleteAll();
    }
}
