package ru.vasilyev.flashcards.statistics;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.vasilyev.flashcards.LoadDataBase;
import ru.vasilyev.flashcards.domain.Statistics;
import ru.vasilyev.flashcards.dto.MappingUtils;
import ru.vasilyev.flashcards.dto.StatisticsDTO;
import ru.vasilyev.flashcards.service.StatisticsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MappingUtils statisticsDTOMapper;

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LoadDataBase loadDataBase;

    @BeforeEach
    void setUp() {
        loadDataBase.initUserDatabase();
        loadDataBase.initCardDatabase();
        loadDataBase.initStatisticsDatabase();
    }

    @AfterEach
    void tearDown() {
        loadDataBase.cleanStatisticsDataBase();
        loadDataBase.cleanCardDataBase();
        loadDataBase.cleanUserDataBase();
    }

    @Test
    void statisticsControllerGetRequests() throws Exception {
        this.mockMvc.perform(get("/statistics")).andExpect(status().isOk());
    }

    @Test
    void statisticsControllerPostRequest() throws Exception { //TODO FIX ME
        Statistics newStatistics = new Statistics("Low");
        this.mockMvc.perform(post("/statistics")
                        .content(objectMapper.writeValueAsString(statisticsDTOMapper.mapToStatisticsDTO(newStatistics)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.knowledgeLevel").value("Low"));
    }

    @Test
    void statisticsControllerPutRequest() throws Exception {
        Statistics replacedStatistics = statisticsService.getStatisticsByKnowledgeLevel("Low").get(0);
        replacedStatistics.setKnowledgeLevel("Perfect");
        Long id = replacedStatistics.getId();
        this.mockMvc.perform(put("/statistics/{id}", id)
                        .content(objectMapper.writeValueAsString(statisticsDTOMapper.mapToStatisticsDTO(replacedStatistics)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.knowledgeLevel").value("Perfect"))
                .andExpect(jsonPath("$.cardId").isNotEmpty());
    }

    @Test
    void statisticsControllerDeleteRequest() throws Exception {
        Long id = statisticsService.getStatisticsByKnowledgeLevel("Low").get(0).getId();
        this.mockMvc.perform(delete("/statistics/{id}", id)).andExpect(status().isOk());
        this.mockMvc.perform(get("/statistics/{id}", id)).andExpect(status().isBadRequest());
    }
}
