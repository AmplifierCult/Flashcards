package ru.vasilyev.flashcards;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import ru.vasilyev.flashcards.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserRepository repository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void userControllerRequests() throws Exception {

        //this.mockMvc.perform(get("/users")).andExpect(status().isOk());

        //RequestBuilder post = ("/users");
        this.mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString("Mike"))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.login").value("Mike"));

    }
}
