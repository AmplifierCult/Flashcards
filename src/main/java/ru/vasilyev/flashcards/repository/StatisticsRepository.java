package ru.vasilyev.flashcards.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.Statistics;

import java.util.List;

public interface StatisticsRepository extends CrudRepository<Statistics, Long> {

    List<Statistics> findByKnowledgeLevel(String deckName);

    Statistics findById(long id);

    Statistics findByCard(Card card); //TODO FIX ME

    List<Statistics> findAll();
}
