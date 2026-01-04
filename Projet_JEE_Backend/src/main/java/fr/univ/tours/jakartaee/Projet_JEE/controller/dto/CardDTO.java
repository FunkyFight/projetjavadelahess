package fr.univ.tours.jakartaee.Projet_JEE.controller.dto;

import fr.univ.tours.jakartaee.Projet_JEE.entities.CardEntity;

public record CardDTO(
        Long id,
        String cardname,
        ExtensionDTO extension,
        String comment,
        Boolean have) {

    public CardDTO(CardEntity card) {
        this(card.getId(), card.getCardName(), new ExtensionDTO(card.getExtension()), card.getComment(), card.isHave());
    }

    public CardDTO(Long id, String cardname, ExtensionDTO extension, String comment) {
        this(id, cardname, extension, comment, false);
    }

    public static CardDTO makeDTO(CardEntity card) {
        return new CardDTO(card.getId(), card.getCardName(), new ExtensionDTO(card.getExtension()), card.getComment(), card.isHave());
    }
}
