package ru.vasilyev.flashcards.dto;

import org.springframework.stereotype.Component;
import ru.vasilyev.flashcards.domain.Card;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Component
public class CardDTO {

    private Long id;

    private byte [] image;

    @NotBlank(message = "word cannot be empty")
    private String word;

    @NotBlank(message = "translatedWord cannot be empty")
    private String translatedWord;

    private Map<String, String> exampleOfUse;

    private Long authorId;
    private byte [] exampleOfPronunciation;

    private String creationDate;

    public CardDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }

    public void setTranslatedWord(String translatedWord) {
        this.translatedWord = translatedWord;
    }

    public Map<String, String> getExampleOfUse() {
        return exampleOfUse;
    }

    public void setExampleOfUse(Map<String, String> exampleOfUse) {
        this.exampleOfUse = exampleOfUse;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public byte[] getExampleOfPronunciation() {
        return exampleOfPronunciation;
    }

    public void setExampleOfPronunciation(byte[] exampleOfPronunciation) {
        this.exampleOfPronunciation = exampleOfPronunciation;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public CardDTO mapToCardDTO(Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(card.getId());

        if(card.getImage() != null) {
            cardDTO.setImage(card.getImage());
        }

        cardDTO.setWord(card.getWord());
        cardDTO.setTranslatedWord(card.getTranslatedWord());

        if(card.getExampleOfUse() != null) {
            cardDTO.setExampleOfUse(card.getExampleOfUse());
        }

        cardDTO.setAuthorId(card.getAuthor().getId());

        if(card.getExampleOfPronunciation() != null) {
            cardDTO.setExampleOfPronunciation(card.getExampleOfPronunciation());
        }

        cardDTO.setCreationDate(card.getCreationDate().toString());
        return cardDTO;
    }

    public Card mapToCard(CardDTO cardDTO){
        String word = cardDTO.getWord();
        Card card = new Card(word);

        if(cardDTO.getTranslatedWord() != null) {
            card.setTranslatedWord(cardDTO.getTranslatedWord());
        }

        if(cardDTO.getImage() != null) {
            card.setImage(cardDTO.getImage());
        }

        if(cardDTO.getExampleOfUse() != null) {
            card.setExampleOfUse(cardDTO.getExampleOfUse());
        }

        if(cardDTO.getExampleOfPronunciation() != null) {
            card.setExampleOfPronunciation(cardDTO.getExampleOfPronunciation());
        }

        return card;
    }
}
