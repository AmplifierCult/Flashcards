package ru.vasilyev.flashcards;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.CardRepository;
import ru.vasilyev.flashcards.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTests {

    private static final Logger log = LoggerFactory.getLogger(CardControllerTests.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    CardRepository cardRepository;

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
    }

    @AfterEach
    void tearDown() {
        loadDataBase.cleanStatisticsDataBase();
        loadDataBase.cleanCardDataBase();
        loadDataBase.cleanUserDataBase();
        loadDataBase.cleanDeckDataBase();
    }

    @Test
    void cardControllerGetRequests() throws Exception {
        this.mockMvc.perform(get("/cards")).andExpect(status().isOk());
    }

    @Test
    void cardControllerPostRequests() throws Exception {
        User author = userRepository.findByLogin("Andrey");
        Card newCard = new Card("Steel", author);
        this.mockMvc.perform(post("/cards")
                        .content(objectMapper.writeValueAsString(newCard))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.word").value("Steel"))
                .andExpect(jsonPath("$.author").value(author));
    }

    @Test
    void cardControllerPutRequest() throws Exception {
        User author = userRepository.findByLogin("Andrey");
        Card newCard = new Card("Metal", author);
        newCard.setTranslatedWord("Металл");
        Long id = cardRepository.findByWord("Rock").getId();
        this.mockMvc.perform(put("/cards/{id}", id)
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
