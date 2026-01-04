package fr.univ.tours.jakartaee.Projet_JEE.controller.dto;


import fr.univ.tours.jakartaee.Projet_JEE.entities.CardEntity;
import fr.univ.tours.jakartaee.Projet_JEE.entities.ExtensionEntity;

import java.util.List;

public record ExtensionDTO(
        Long id,
        String extname,
        List<CardDTO> cards,
        Integer date,
        EraDTO era) {

    public ExtensionDTO(ExtensionEntity extension){
        this(extension.getId(), extension.getExtName(), extension.getCards().stream().map(CardDTO::new).toList(), extension.getDate(), new EraDTO(extension.getEra()));
    }

    public static ExtensionDTO makeDTO(ExtensionEntity extension) {
        return new ExtensionDTO(extension.getId(), extension.getExtName(), extension.getCards().stream().map(CardDTO::new).toList(), extension.getDate(), new EraDTO(extension.getEra()));
    }
}
