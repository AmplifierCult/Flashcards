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

    public void initUserDatabase() {
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

    public void cleanUserDataBase() {
        userRepository.deleteAll();
    }

    public void initCardDatabase() {
        User author = userRepository.findByLogin("Andrey");
        Card firstCard = new Card("Wood");
        firstCard.setAuthor(author);
        firstCard.setTranslatedWord("Древесина");
        firstCard.setCreationDate(Instant.now());

        Card secondCard = new Card("Iron");
        secondCard.setAuthor(author);
        secondCard.setTranslatedWord("Железо");
        secondCard.setCreationDate(Instant.now());

        Card thirdCard = new Card("Water");
        thirdCard.setAuthor(author);
        thirdCard.setTranslatedWord("Вода");
        thirdCard.setCreationDate(Instant.now());

        Card fourthCard = new Card("Rock");
        fourthCard.setAuthor(author);
        fourthCard.setTranslatedWord("Камень");
        fourthCard.setCreationDate(Instant.now());

        log.info("Preloading " + cardRepository.save(firstCard));
        log.info("Preloading " + cardRepository.save(secondCard));
        log.info("Preloading " + cardRepository.save(thirdCard));
        log.info("Preloading " + cardRepository.save(fourthCard));
    }

    public void cleanCardDataBase() {
        cardRepository.deleteAll();
    }

    public void initStatisticsDatabase() {
        User author = userRepository.findByLogin("Andrey");

        Card firstCard = cardRepository.findByWord("Wood");
        Card secondCard = cardRepository.findByWord("Iron");
        Card thirdCard = cardRepository.findByWord("Water");
        Card fourthCard = cardRepository.findByWord("Rock");

        Statistics firstStatistics = new Statistics("Low");
        firstStatistics.setUser(author);
        firstStatistics.setCard(firstCard);

        Statistics secondStatistics = new Statistics("Middle");
        secondStatistics.setUser(author);
        secondStatistics.setCard(secondCard);

        Statistics thirdStatistics = new Statistics("High");
        thirdStatistics.setUser(author);
        thirdStatistics.setCard(thirdCard);

        Statistics fourthStatistics = new Statistics("Perfect");
        fourthStatistics.setUser(author);
        fourthStatistics.setCard(fourthCard);

        log.info("Preloading " + statisticsRepository.save(firstStatistics));
        log.info("Preloading " + statisticsRepository.save(secondStatistics));
        log.info("Preloading " + statisticsRepository.save(thirdStatistics));
        log.info("Preloading " + statisticsRepository.save(fourthStatistics));
    }

    public void cleanStatisticsDataBase() {
        statisticsRepository.deleteAll();
    }

    public void initDeckDatabase() {
        User author = userRepository.findByLogin("Andrey");

        Deck firstDeck = new Deck("Metals");
        firstDeck.setAuthor(author);
        firstDeck.setCreationDate(Instant.now());

        Deck secondDeck = new Deck("Weather");
        secondDeck.setAuthor(author);
        secondDeck.setCreationDate(Instant.now());

        Deck thirdDeck = new Deck("Types of wood");
        thirdDeck.setAuthor(author);
        thirdDeck.setCreationDate(Instant.now());

        Deck fourthDeck = new Deck("Types of animals");
        fourthDeck.setAuthor(author);
        fourthDeck.setCreationDate(Instant.now());

        log.info("Preloading " + deckRepository.save(firstDeck));
        log.info("Preloading " + deckRepository.save(secondDeck));
        log.info("Preloading " + deckRepository.save(thirdDeck));
        log.info("Preloading " + deckRepository.save(fourthDeck));
    }

    public void cleanDeckDataBase() {
        deckRepository.deleteAll();
    }
}
