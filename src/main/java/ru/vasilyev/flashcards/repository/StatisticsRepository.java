package ru.vasilyev.flashcards.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vasilyev.flashcards.domain.Statistics;

public interface StatisticsRepository extends CrudRepository<Statistics, Long> {

    Statistics findByKnowledgeLevel(String deckName);

    Statistics findById(long id);
}
