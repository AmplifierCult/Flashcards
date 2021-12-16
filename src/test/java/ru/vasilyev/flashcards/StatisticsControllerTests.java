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
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.CardRepository;
import ru.vasilyev.flashcards.repository.StatisticsRepository;
import ru.vasilyev.flashcards.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    StatisticsRepository statisticsRepository;

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
        loadDataBase.initStatisticsDatabase();
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
    void statisticsControllerGetRequests() throws Exception {
        this.mockMvc.perform(get("/statistics")).andExpect(status().isOk());
    }

    @Test
    void statisticsControllerPostRequest() throws Exception {
        this.mockMvc.perform(post("/statistics")
                        .content(objectMapper.writeValueAsString("Low"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.knowledgeLevel").value("Low"));
    }

    @Test
    void statisticsControllerPutRequest() throws Exception {
        Statistics newStatistics = new Statistics("High");
        Long authorId = userRepository.findByLogin("Andrey").getId();
        Card newCard = new Card("Gold", authorId);
        cardRepository.save(newCard);
        newStatistics.setCard(newCard);
        Long id = statisticsRepository.findByKnowledgeLevel("Perfect").get(0).getId();
        this.mockMvc.perform(put("/statistics/{id}", id)
                        .content(objectMapper.writeValueAsString(newStatistics))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.knowledgeLevel").value("High"))
                .andExpect(jsonPath("$.card").isNotEmpty());
    }

    @Test
    void statisticsControllerDeleteRequest() throws Exception {
        Long id = statisticsRepository.findByKnowledgeLevel("Low").get(0).getId();
        this.mockMvc.perform(delete("/statistics/{id}", id)).andExpect(status().isOk());
        this.mockMvc.perform(get("/statistics/{id}", id)).andExpect(status().isBadRequest());
    }
}
