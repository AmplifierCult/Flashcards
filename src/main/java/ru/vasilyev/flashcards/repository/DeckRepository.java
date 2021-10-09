package ru.vasilyev.flashcards.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vasilyev.flashcards.domain.Deck;

public interface DeckRepository extends CrudRepository<Deck, Long> {
    Deck findByDeckName(String deckName);

    Deck findById(long id);
}
