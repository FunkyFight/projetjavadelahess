package fr.univ.tours.jakartaee.Projet_JEE.services;


import fr.univ.tours.jakartaee.Projet_JEE.controller.dto.CardDTO;
import fr.univ.tours.jakartaee.Projet_JEE.entities.CardEntity;
import fr.univ.tours.jakartaee.Projet_JEE.entities.ExtensionEntity;
import fr.univ.tours.jakartaee.Projet_JEE.repository.CardRepository;
import fr.univ.tours.jakartaee.Projet_JEE.repository.ExtensionRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CardService {

    private final CardRepository cardRepository;
    private final ExtensionRepository extensionRepository;
    private EntityManager em;

    public CardService(CardRepository cardRepository, ExtensionRepository extensionRepository, EntityManager em) {
        this.cardRepository = cardRepository;
        this.extensionRepository = extensionRepository;
        this.em = em;
    }

    public List<CardEntity> searchCard(CardDTO card) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CardEntity> cq = cb.createQuery(CardEntity.class);
        Root<CardEntity> root = cq.from(CardEntity.class);
        List<Predicate> predicates = new ArrayList<>();
        if (card.id() != null) {
            predicates.add(cb.equal(root.get("id"), card.id()));
        }
        if (card.cardname() != null && !card.cardname().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("cardname")), "%" + card.cardname().toLowerCase() + "%"));
        }
        if (card.extension() != null) {
            predicates.add(cb.equal(root.get("extension"), card.extension()));
        }
        if (card.comment() != null && !card.comment().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("comment")), "%" + card.comment().toLowerCase() + "%"));
        }
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }
        return em.createQuery(cq).getResultList();
    }

    public CardDTO createNewCard(CardDTO card) {
        CardEntity cardEntity = new CardEntity(card);
        return CardDTO.makeDTO(cardRepository.save(cardEntity));
    }

    public void updateCard(Long id, String name, ExtensionEntity extension, String comment, Boolean have) {
        Optional<CardEntity> cardEntity = cardRepository.findById(id);
        cardEntity.get().setCardName(name);
        cardEntity.get().setExtension(extension);
        cardEntity.get().setComment(comment);
        cardEntity.get().setHave(have);
        cardRepository.save(cardEntity.get());
    }

    public void deleteById(Long id) {
        cardRepository.deleteById(id);
    }
}
