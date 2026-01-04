package fr.univ.tours.jakartaee.Projet_JEE.entities;

import fr.univ.tours.jakartaee.Projet_JEE.controller.dto.ExtensionDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="extension")
public class ExtensionEntity {

    @Id
    @Column
    private Long id;

    @Column(nullable = false)
    private String extname;

    @OneToMany(targetEntity=CardEntity.class, mappedBy = "extension")
    private List<CardEntity> cards = new ArrayList<>();

    @Column
    private Integer date;

    @ManyToOne
    @JoinColumn(name="eraname", nullable = false)
    private EraEntity era;

    public ExtensionEntity() {}

    public ExtensionEntity(ExtensionDTO dto){
        this.id = dto.id();
        this.extname = dto.extname();
        this.date = dto.date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getExtName() {
        return extname;
    }

    public void setExtName(String name) {
        this.extname = name;
    }

    public List<CardEntity> getCards() {
        return cards;
    }

    public void setCards(List<CardEntity> cards) {
        this.cards = cards;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public EraEntity getEra() {
        return era;
    }

    public void setEra(EraEntity era) {
        this.era = era;
    }
}
