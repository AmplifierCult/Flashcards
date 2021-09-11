package ru.vasilyev.flashcards.domain;

import java.util.Map;

public class Card {
    byte [] image;
    String word;
    String translatedWord;
    Map<String, String> exampleOfUse;
    User author;
    byte [] exampleOfPronunciation;
}
