package ru.vasilyev.flashcards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.UserRepository;

/**
 * Flashcards
 *
 */
@SpringBootApplication
public class FlashcardsApplication {

	private static final Logger log = LoggerFactory.getLogger(FlashcardsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FlashcardsApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner demo(UserRepository repository) {
//		return (args) -> {
//			// save a few users
//			repository.save(new User("Jack"));
//			repository.save(new User("Chloe"));
//			repository.save(new User("Kim"));
//			repository.save(new User("David"));
//			repository.save(new User("Michelle"));
//
//			// fetch all users
//			log.info("Users found with findAll():");
//			log.info("-------------------------------");
//			for (User user : repository.findAll()) {
//				log.info(user.toString());
//			}
//			log.info("");
//
//			// fetch an individual user by ID
//			User user = repository.findById(1L);
//			log.info("User found with findById(1L):");
//			log.info("--------------------------------");
//			log.info(user.toString());
//			log.info("");
//
//			// fetch users by login
//			log.info("User found with findByLogin('Bauer'):");
//			log.info("--------------------------------------------");
//			/*repository.findByLogin("Bauer").forEach(bauer -> {
//				log.info(bauer.toString());
//			});*/
//
//			log.info("");
//		};
//	}
}
