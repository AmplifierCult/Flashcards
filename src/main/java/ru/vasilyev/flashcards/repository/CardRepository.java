package ru.vasilyev.flashcards.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vasilyev.flashcards.domain.Card;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Long> {

    Card findByWord(String word);

    Card findById(long id);

    List<Card> findAll();
}
