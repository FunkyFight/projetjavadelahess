package fr.univ.tours.jakartaee.Projet_JEE.controller.dto;

import fr.univ.tours.jakartaee.Projet_JEE.entities.CardEntity;
import fr.univ.tours.jakartaee.Projet_JEE.entities.EraEntity;

import java.util.List;

public record EraDTO(
        Long id,
        String eraname,
        List<ExtensionDTO> extensions
) {
    public EraDTO(EraEntity era) {
        this(era.getId(), era.getEraName(), era.getExtensions().stream().map(ExtensionDTO::new).toList());
    }

    public static EraDTO makeDTO(EraEntity era) {
        return new EraDTO(era.getId(), era.getEraName(), era.getExtensions().stream().map(ExtensionDTO::new).toList());
    }
}