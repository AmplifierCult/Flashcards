package ru.vasilyev.flashcards;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    LoadDataBase loadDataBase;

    @BeforeEach
    void setUp() {
        loadDataBase.initUserDatabase();
    }

    @AfterEach
    void tearDown() {
        loadDataBase.cleanUserDataBase();
    }

    @Test
    void userControllerRequests() throws Exception {

        //GET
        this.mockMvc.perform(get("/users")).andExpect(status().isOk());

        //POST
        this.mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString("Mike"))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.login").value("Mike"));

        //PUT
        User newUser = new User("Arnold");
        newUser.setEmail("arnold123@mail.ru");
        this.mockMvc.perform(put("/users/3")
                .content(objectMapper.writeValueAsString(newUser))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.login").value("Arnold"))
                .andExpect(jsonPath("$.email").value("arnold123@mail.ru"));

        //DELETE
        this.mockMvc.perform(delete("/user/3")).andExpect(status().isOk());
        this.mockMvc.perform(get("/user/3")).andExpect(status().isMethodNotAllowed());
    }
}
