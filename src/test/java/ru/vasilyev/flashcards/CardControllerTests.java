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
import ru.vasilyev.flashcards.repository.CardRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LoadDataBase loadDataBase;

    @BeforeEach
    void setUp() {
        loadDataBase.initCardDatabase();
    }

    @AfterEach
    void tearDown() {
        loadDataBase.cleanStatisticsDataBase();
        loadDataBase.cleanCardDataBase();
        loadDataBase.cleanUserDataBase();
        loadDataBase.cleanDeckDataBase();
    }

    @Test
    void cardControllerRequests() throws Exception {

        //GET
        this.mockMvc.perform(get("/cards")).andExpect(status().isOk());

        //POST
        this.mockMvc.perform(post("/cards")
                        .content(objectMapper.writeValueAsString("Steel"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.word").value("Steel"));

        //PUT
        Card newCard = new Card("Metal");
        newCard.setTranslatedWord("Металл");
        this.mockMvc.perform(put("/cards/4")
                        .content(objectMapper.writeValueAsString(newCard))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.word").value("Metal"))
                .andExpect(jsonPath("$.translatedWord").value("Металл"));
    }

    @Test
    void cardControllerDeleteRequest() throws Exception {
        Long id = cardRepository.findByWord("Wood").getId();
        this.mockMvc.perform(delete("/cards/{id}", id)).andExpect(status().isOk());
        this.mockMvc.perform(get("/cards/{id}", id)).andExpect(status().isBadRequest());
    }
}
