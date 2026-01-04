package fr.univ.tours.jakartaee.Projet_JEE.entities;

import fr.univ.tours.jakartaee.Projet_JEE.controller.dto.CardDTO;
import jakarta.persistence.*;

@Entity
@Table(name="card")
public class CardEntity {

    @Id
    @Column
    private Long id;

    @Column(nullable = false)
    private String cardname;

    @ManyToOne
    @JoinColumn(name="name", nullable = false)
    private ExtensionEntity extension;

    @Column
    private String comment;

    @Column
    private boolean have;

    public CardEntity() {}

    public CardEntity(CardDTO dto){
        this.id = dto.id();
        this.cardname = dto.cardname();
        this.comment = dto.comment();
        this.have = dto.have();
    }
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

    public ExtensionEntity getExtension() {
        return extension;
    }

    public void setExtension(ExtensionEntity extension) {
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
