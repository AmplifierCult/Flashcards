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

import java.time.Instant;

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
        User firstUser = new User("Andrey");
        firstUser.setPassword("qwerty");
        firstUser.setRegistrationDate(Instant.now());
        firstUser.setLastActionDate(Instant.now());

        log.info("Preloading " + userRepository.save(firstUser));

        User secondUser = new User("Ivan");
        secondUser.setPassword("123456");
        secondUser.setRegistrationDate(Instant.now());
        secondUser.setLastActionDate(Instant.now());
        log.info("Preloading " + userRepository.save(secondUser));

        User thirdUser = new User("Fedor");
        thirdUser.setPassword("asdf");
        thirdUser.setRegistrationDate(Instant.now());
        thirdUser.setLastActionDate(Instant.now());
        log.info("Preloading " + userRepository.save(thirdUser));

        User fourthUser = new User("Igor");
        fourthUser.setPassword("zxcv");
        fourthUser.setRegistrationDate(Instant.now());
        fourthUser.setLastActionDate(Instant.now());
        log.info("Preloading " + userRepository.save(fourthUser));
    }

    void cleanUserDataBase() {
        userRepository.deleteAll();
    }

    void initCardDatabase() {
        User author = userRepository.findByLogin("Andrey");
        Card firstCard = new Card("Wood", author);
        firstCard.setCreationDate(Instant.now());
        Card secondCard = new Card("Iron", author);
        secondCard.setCreationDate(Instant.now());
        Card thirdCard = new Card("Water", author);
        thirdCard.setCreationDate(Instant.now());
        Card fourthCard = new Card("Rock", author);
        fourthCard.setCreationDate(Instant.now());

        log.info("Preloading " + cardRepository.save(firstCard));
        log.info("Preloading " + cardRepository.save(secondCard));
        log.info("Preloading " + cardRepository.save(thirdCard));
        log.info("Preloading " + cardRepository.save(fourthCard));
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
