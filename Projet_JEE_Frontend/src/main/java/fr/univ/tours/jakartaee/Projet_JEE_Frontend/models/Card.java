package fr.univ.tours.jakartaee.Projet_JEE_Frontend.models;

public class Card {

    private Long id;
    private String cardname;
    private Extension extension;
    private String comment;
    private boolean have;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardName() {
        return cardname;
    }

    public void setCardName(String name) {
        this.cardname = name;
    }

    public Extension getExtension() {
        return extension;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isHave() {
        return have;
    }

    public void setHave(boolean have) {
        this.have = have;
    }
}
