package ru.vasilyev.flashcards.domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vasilyev.flashcards.repository.CardRepository;
import ru.vasilyev.flashcards.repository.UserRepository;

@Configuration
public class LoadDataBase {

    private static final Logger log = LoggerFactory.getLogger(LoadDataBase.class);

    @Bean
    CommandLineRunner initUserDatabase(UserRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new User("Andrey")));
            log.info("Preloading " + repository.save(new User("Ivan")));
            log.info("Preloading " + repository.save(new User("Fedor")));
            log.info("Preloading " + repository.save(new User("Igor")));
        };
    }

    @Bean
    CommandLineRunner initCardDatabase(CardRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Card("Wood")));
            log.info("Preloading " + repository.save(new Card("Iron")));
            log.info("Preloading " + repository.save(new Card("Water")));
            log.info("Preloading " + repository.save(new Card("Rock")));
        };
    }

}
