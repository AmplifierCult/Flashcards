package ru.vasilyev.flashcards.dto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Component
public class UserDTO {

    private Long id;

    @NotBlank(message = "login cannot be empty")
    private String login;

    @NotBlank(message = "password cannot be empty")
    private String password;

    private String email;

    @NotBlank(message = "registrationDate cannot be null")
    private String registrationDate;

    private String lastActionDate;

    private List<String> deckNames;

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getLastActionDate() {
        return lastActionDate;
    }

    public void setLastActionDate(String lastActionDate) {
        this.lastActionDate = lastActionDate;
    }

    public List<String> getDeckNames() {
        return deckNames;
    }

    public void setDeckNames(List<String> deckNames) {
        this.deckNames = deckNames;
    }
}
