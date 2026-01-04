package fr.univ.tours.jakartaee.Projet_JEE_Frontend.dto;

import fr.univ.tours.jakartaee.Projet_JEE_Frontend.models.Extension;

import java.util.List;

public record ExtensionDTO(
        Long id,
        String extname,
        List<CardDTO> cards,
        Integer date,
        EraDTO era) {

    public ExtensionDTO(Extension extension){
        this(extension.getId(), extension.getExtName(), extension.getCards().stream().map(CardDTO::new).toList(), extension.getDate(), new EraDTO(extension.getEra()));
    }

    public static ExtensionDTO makeDTO(Extension extension) {
        return new ExtensionDTO(extension.getId(), extension.getExtName(), extension.getCards().stream().map(CardDTO::new).toList(), extension.getDate(), new EraDTO(extension.getEra()));
    }
}
