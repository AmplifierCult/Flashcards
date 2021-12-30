package ru.vasilyev.flashcards.сard;

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
import ru.vasilyev.flashcards.LoadDataBase;
import ru.vasilyev.flashcards.dto.CardDTO;
import ru.vasilyev.flashcards.service.CardService;

import java.util.HashMap;

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
    CardService cardService;

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
    void GetAllCards() throws Exception {
        this.mockMvc.perform(get("/cards")).andExpect(status().isOk());
    }

    @Test
    void GetCardById() throws Exception {
        Long id = cardService.getCardByWord("Wood").getId();
        this.mockMvc.perform(get("/cards/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value("Wood"));
    }

    @Test
    void GetCardByWord() throws Exception {
        String word = "Wood";
        Long id = cardService.getCardByWord(word).getId();
        this.mockMvc.perform(get("/cards/search?word={word}", word))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void cardControllerPostRequests() throws Exception {
        CardDTO newCardDTO = new CardDTO();
        newCardDTO.setWord("Steel");
        newCardDTO.setTranslatedWord("Сталь");
        newCardDTO.setExampleOfUse(new HashMap<>(){{
            put("noun", "Blades of steel.");
            put("adjective", "Steel sword.");
        }});
        this.mockMvc.perform(post("/cards")
                        .content(objectMapper.writeValueAsString(newCardDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.word").value("Steel"))
                .andExpect(jsonPath("$.authorId").isNotEmpty());
    }

    @Test
    void cardControllerPutRequest() throws Exception {
        CardDTO newCardDTO = new CardDTO();
        newCardDTO.setWord("Metal");
        newCardDTO.setTranslatedWord("Металл");
        Long id = cardService.getCardByWord("Rock").getId();
        this.mockMvc.perform(put("/cards/{id}", id)
                        .content(objectMapper.writeValueAsString(newCardDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.word").value("Metal"))
                .andExpect(jsonPath("$.translatedWord").value("Металл"));
    }

    @Test
    void cardControllerDeleteRequest() throws Exception {
        Long id = cardService.getCardByWord("Wood").getId();
        this.mockMvc.perform(delete("/cards/{id}", id)).andExpect(status().isOk());
        this.mockMvc.perform(get("/cards/{id}", id)).andExpect(status().isBadRequest());
    }
}
