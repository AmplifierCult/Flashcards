package ru.vasilyev.flashcards;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.vasilyev.flashcards.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        this.mockMvc.perform(get("/users")).andExpect(status().isOk());


//        this.mockMvc.perform(post("path/to/insert/student"))
//                .accept(MediaType.APPLICATION_JSON)
//                .andExpect(status().isOk())
//                .andExpect(content().string("{}"));;


    }
}
