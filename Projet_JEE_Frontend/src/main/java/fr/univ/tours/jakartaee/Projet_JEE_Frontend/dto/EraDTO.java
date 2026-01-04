package fr.univ.tours.jakartaee.Projet_JEE_Frontend.dto;

import fr.univ.tours.jakartaee.Projet_JEE_Frontend.models.Era;

import java.util.List;

public record EraDTO(
        Long id,
        String eraname,
        List<ExtensionDTO> extensions) {
    public EraDTO(Era era) {
        this(era.getId(), era.getEraname(), era.getExtensions().stream().map(ExtensionDTO::new).toList());
    }

    public static EraDTO makeDTO(Era era) {
        return new EraDTO(era.getId(), era.getEraname(), era.getExtensions().stream().map(ExtensionDTO::new).toList());
    }
}
