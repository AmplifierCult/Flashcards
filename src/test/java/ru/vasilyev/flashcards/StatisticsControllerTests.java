package ru.vasilyev.flashcards;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.vasilyev.flashcards.domain.Card;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.repository.StatisticsRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    StatisticsRepository repository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void statisticsControllerRequests() throws Exception {

        //GET
        this.mockMvc.perform(get("/statistics")).andExpect(status().isOk());

        //POST
        this.mockMvc.perform(post("/statistics")
                        .content(objectMapper.writeValueAsString("Low"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.knowledgeLevel").value("Low"));

        //PUT
        Statistics newStatistics = new Statistics("High");
//        Card newCard = new Card("Gold");
//        newStatistics.setCard(newCard);
        this.mockMvc.perform(put("/statistics/10")
                        .content(objectMapper.writeValueAsString(newStatistics))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.knowledgeLevel").value("High"));
//                .andExpect(jsonPath("$.card").isNotEmpty());

        //DELETE
        this.mockMvc.perform(delete("/statistics/12")).andExpect(status().isOk());
        this.mockMvc.perform(get("/statistics/12")).andExpect(status().isBadRequest());
    }
}
