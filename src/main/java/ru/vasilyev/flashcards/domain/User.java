package ru.vasilyev.flashcards.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "login cannot be empty")
    @Column(unique = true)
    private String login;

    @NotBlank(message = "password cannot be empty")
    private String password;

    private String email;

    @NotNull(message = "registrationDate cannot be null")
    private Instant registrationDate;

    private Instant lastActionDate;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "User_Deck",
            joinColumns = {@JoinColumn(name = "User")},
            inverseJoinColumns = {@JoinColumn(name = "Deck")}
    )
    @OrderColumn
    private List <Deck> decks;

    protected User() {
    }

    public User(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Instant getLastActionDate() {
        return lastActionDate;
    }

    public void setLastActionDate(Instant lastActionDate) {
        this.lastActionDate = lastActionDate;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate='" + DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss Z").withZone(ZoneId.systemDefault()).format(registrationDate) + '\'' +
                '}';
    }
}
