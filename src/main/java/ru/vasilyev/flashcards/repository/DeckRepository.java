package ru.vasilyev.flashcards.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.domain.User;

import java.util.List;

public interface DeckRepository extends CrudRepository<Deck, Long> {
    Deck findByDeckName(String deckName);

    Deck findById(long id);

    List<Deck> findAll();

    List<Deck> findByAuthor(User author);
}
