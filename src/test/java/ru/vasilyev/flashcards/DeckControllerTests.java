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
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.Deck;
import ru.vasilyev.flashcards.repository.CardRepository;
import ru.vasilyev.flashcards.repository.DeckRepository;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    MockMvc mockMvc;

    @Autowired
    LoadDataBase loadDataBase;

    @BeforeEach
    void setUp() {
        loadDataBase.initDeckDatabase();
    }

    @AfterEach
    void tearDown() {
        loadDataBase.cleanStatisticsDataBase();
        loadDataBase.cleanCardDataBase();
        loadDataBase.cleanUserDataBase();
        loadDataBase.cleanDeckDataBase();
    }

    @Test
    void deckControllerRequests() throws Exception {
        //GET
        this.mockMvc.perform(get("/decks")).andExpect(status().isOk());

        //POST
        this.mockMvc.perform(post("/decks")
                        .content(objectMapper.writeValueAsString("Engineering"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.deckName").value("Engineering"));

        //PUT
        this.mockMvc.perform(put("/decks/4")
                        .content(objectMapper.writeValueAsString("Space"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.deckName").value("Space"));
    }

    @Test
    void deckControllerDeleteRequest() throws Exception {
        Long id = deckRepository.findByDeckName("Weather").getId();
        this.mockMvc.perform(delete("/decks/{id}", id)).andExpect(status().isOk());
        this.mockMvc.perform(get("/decks/{id}", id)).andExpect(status().isBadRequest());
    }
}
