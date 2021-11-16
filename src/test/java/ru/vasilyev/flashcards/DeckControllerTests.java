package ru.vasilyev.flashcards;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.repository.CardRepository;

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
    CardRepository repository;

    @Autowired
    MockMvc mockMvc;

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
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.deckName").value("Engineering"));

        //PUT
        this.mockMvc.perform(put("/decks/15")
                        .content(objectMapper.writeValueAsString("Space"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").value(15))
                .andExpect(jsonPath("$.deckName").value("Space"));

        //DELETE
        this.mockMvc.perform(delete("/decks/16")).andExpect(status().isOk());
        this.mockMvc.perform(get("/decks/16")).andExpect(status().isBadRequest());
    }
}
