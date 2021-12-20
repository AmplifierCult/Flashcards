package ru.vasilyev.flashcards;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.DeckRepository;
import ru.vasilyev.flashcards.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DeckControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LoadDataBase loadDataBase;

    @BeforeEach
    void setUp() {
        loadDataBase.initUserDatabase();
        loadDataBase.initCardDatabase();
        loadDataBase.initDeckDatabase();
    }

    @AfterEach
    void tearDown() {
        loadDataBase.cleanDeckDataBase();
        loadDataBase.cleanCardDataBase();
        loadDataBase.cleanUserDataBase();
    }

    @Test
    void deckControllerGetRequests() throws Exception {
        this.mockMvc.perform(get("/decks")).andExpect(status().isOk());
    }

    @Test
    void deckControllerPostRequests() throws Exception {
        User author = userRepository.findByLogin("Andrey");
        Deck newDeck = new Deck("Engineering", author);

        this.mockMvc.perform(post("/decks")
                        .content(objectMapper.writeValueAsString(newDeck))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.deckName").value("Engineering"));
    }

    @Test
    void deckControllerPutRequests() throws Exception {
        Deck replacedDeck = deckRepository.findByDeckName("Types of animals");
        replacedDeck.setDeckName("Space");
        Long id = replacedDeck.getId();
        this.mockMvc.perform(put("/decks/{id}", id)
                        .content(objectMapper.writeValueAsString(replacedDeck))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.deckName").value("Space"));
    }

    @Test
    void deckControllerDeleteRequest() throws Exception {
        Long deckId = deckRepository.findByDeckName("Weather").getId();
        this.mockMvc.perform(delete("/decks/{id}", deckId)).andExpect(status().isOk());
        this.mockMvc.perform(get("/decks/{id}", deckId)).andExpect(status().isBadRequest());
    }
}
