package ru.vasilyev.flashcards.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

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

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, firstName='%s', e-mail='%s']",
                id, login, email);
    }



    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
