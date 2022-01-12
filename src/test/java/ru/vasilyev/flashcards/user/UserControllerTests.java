package ru.vasilyev.flashcards.user;

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
import ru.vasilyev.flashcards.LoadDataBase;
import ru.vasilyev.flashcards.domain.User;
import ru.vasilyev.flashcards.dto.MappingUtils;
import ru.vasilyev.flashcards.dto.UserDTO;
import ru.vasilyev.flashcards.dto.mapper.UserMapper;
import ru.vasilyev.flashcards.repository.UserRepository;
import ru.vasilyev.flashcards.service.DeckService;
import ru.vasilyev.flashcards.service.UserService;

import java.time.Instant;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    DeckService deckService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LoadDataBase loadDataBase;

    @BeforeEach
    void setUp() {
        loadDataBase.initUserDatabase();
        loadDataBase.initDeckDatabase();

    }

    @AfterEach
    void tearDown() {
        loadDataBase.cleanStatisticsDataBase();
        loadDataBase.cleanCardDataBase();
        loadDataBase.cleanDeckDataBase();
        loadDataBase.cleanUserDataBase();
    }

    @Test
    void GetAllUsers() throws Exception {
        this.mockMvc.perform(get("/users")).andExpect(status().isOk());
    }

    @Test
    void GetUserById() throws Exception {
        Long id = userService.getUserByLogin("Andrey").getId();
        this.mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("Andrey"));
    }

    @Test
    void GetUserByLogin() throws Exception {
        String login = "Andrey";
        Long id = userService.getUserByLogin(login).getId();
        this.mockMvc.perform(get("/users/search?login={login}", login))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void userControllerPostRequests() throws Exception {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setLogin("Mike");
        newUserDTO.setPassword("qwerty");
        this.mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(newUserDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.login").value("Mike"));
    }

    @Test
    void userControllerPutRequests() throws Exception {
        User newUser = new User("Arnold");
        newUser.setEmail("arnold123@mail.ru");
        newUser.setPassword("12356");
        newUser.setRegistrationDate(Instant.now());
        newUser.setLastActionDate(Instant.now());
        newUser.setDecks(List.of(deckService.getDeckByDeckName("Metals")));

        Long id = userService.getUserByLogin("Fedor").getId();

        this.mockMvc.perform(put("/users/{id}", id)
                        .content(objectMapper.writeValueAsString(userMapper.mapToUserDTO(newUser)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.login").value("Arnold"))
                .andExpect(jsonPath("$.email").value("arnold123@mail.ru"))
                .andExpect(jsonPath("$.decksDTO").isNotEmpty());
    }

    @Test
    void userControllerDeleteRequests() throws Exception {
        Long id = userService.getUserByLogin("Igor").getId();
        this.mockMvc.perform(delete("/user/{id}", id)).andExpect(status().isOk());
        this.mockMvc.perform(get("/user/{id}", id)).andExpect(status().isMethodNotAllowed());
    }
}
