package fr.univ.tours.jakartaee.Projet_JEE_Frontend.models;

import java.util.ArrayList;
import java.util.List;

public class Extension {

    private Long id;
    private String extname;
    private List<Card> cards = new ArrayList<>();
    private Integer date;
    private Era era;


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getExtName() { return extname; }

    public void setExtName(String name) { this.extname = name; }

    public List<Card> getCards() { return cards; }

    public void setCards(List<Card> cards) { this.cards = cards; }

    public Integer getDate() { return date; }

    public void setDate(Integer date) { this.date = date; }

    public Era getEra() { return era; }

    public void setEra(Era era) { this.era = era; }
}
