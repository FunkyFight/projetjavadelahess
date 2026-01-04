package fr.univ.tours.jakartaee.Projet_JEE.entities;

import fr.univ.tours.jakartaee.Projet_JEE.controller.dto.EraDTO;
import fr.univ.tours.jakartaee.Projet_JEE.controller.dto.ExtensionDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "era")
public class EraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String eraname;

    @OneToMany(targetEntity = ExtensionEntity.class, mappedBy = "era")
    private List<ExtensionEntity> extensions = new ArrayList<>();

    public EraEntity() {
    }

    public EraEntity(EraDTO dto) {
        this.id = dto.id();
        this.eraname = dto.eraname();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEraName() {
        return eraname;
    }

    public void setEraName(String name) {
        this.eraname = name;
    }

    public List<ExtensionEntity> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<ExtensionEntity> extensions) {
        this.extensions = extensions;
    }
}
