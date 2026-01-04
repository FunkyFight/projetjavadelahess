package fr.univ.tours.jakartaee.Projet_JEE_Frontend.dto;

import fr.univ.tours.jakartaee.Projet_JEE_Frontend.models.Card;

public record CardDTO(
                      Long id,
                      String cardname,
                      ExtensionDTO extension,
                      String comment,
                      Boolean have) {

    public CardDTO(Card card) {
        this(card.getId(), card.getCardName(), new ExtensionDTO(card.getExtension()), card.getComment(), card.isHave());
    }

    public CardDTO(Long id, String cardname, ExtensionDTO extension, String comment) {
        this(id, cardname, extension, comment, false);
    }

    public static CardDTO makeDTO(Card card) {
        return new CardDTO(card.getId(), card.getCardName(), new ExtensionDTO(card.getExtension()), card.getComment(), card.isHave());
    }
}
