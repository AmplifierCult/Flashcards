package ru.vasilyev.flashcards.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String login;
    String password;
    String email;
    Instant registrationDate;
    Instant lastActionDate;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "User_Deck",
            joinColumns = {@JoinColumn(name = "User")},
            inverseJoinColumns = {@JoinColumn(name = "Deck")}
    )
    @OrderColumn
    List <Deck> decks;

    protected User() {
    }

    public User(String login, String email) {
        this.login = login;
        this.email = email;
    }

    public User(String login) {
        this.login = login;
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
                ", email='" + email + '\'' +
                '}';
    }
}
