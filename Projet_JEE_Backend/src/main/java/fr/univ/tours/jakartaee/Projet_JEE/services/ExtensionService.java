package fr.univ.tours.jakartaee.Projet_JEE.services;


import fr.univ.tours.jakartaee.Projet_JEE.controller.dto.ExtensionDTO;
import fr.univ.tours.jakartaee.Projet_JEE.entities.CardEntity;
import fr.univ.tours.jakartaee.Projet_JEE.entities.EraEntity;
import fr.univ.tours.jakartaee.Projet_JEE.entities.ExtensionEntity;
import fr.univ.tours.jakartaee.Projet_JEE.repository.ExtensionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ExtensionService {

    private final ExtensionRepository extensionRepository;
    private EntityManager em;

    public ExtensionService(ExtensionRepository extensionRepository) {
        this.extensionRepository = extensionRepository;
    }

    public List<ExtensionEntity> findAllByExtensionId(ExtensionDTO extension) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ExtensionEntity> cq = cb.createQuery(ExtensionEntity.class);
        Root<ExtensionEntity> root = cq.from(ExtensionEntity.class);
        List<Predicate> predicates = new ArrayList<>();
        Long id = extension.id();
        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }
        cq.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }

    public List<ExtensionEntity> findAllByExtensionName(ExtensionDTO extension) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ExtensionEntity> cq = cb.createQuery(ExtensionEntity.class);
        Root<ExtensionEntity> root = cq.from(ExtensionEntity.class);
        List<Predicate> predicates = new ArrayList<>();
        String name = extension.extname();
        if (name != null && !name.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("extname")), "%" + name.toLowerCase() + "%"));
        }
        cq.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }

    public List<ExtensionEntity> findAllByEraName(ExtensionDTO extension) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ExtensionEntity> cq = cb.createQuery(ExtensionEntity.class);
        Root<ExtensionEntity> root = cq.from(ExtensionEntity.class);
        List<Predicate> predicates = new ArrayList<>();
        String name = extension.era().eraname();
        Join<ExtensionEntity, EraEntity> join = root.join("era");
        if (name != null && !name.isBlank()) {
            predicates.add(cb.like(cb.lower(join.get("eraname")), "%" + name + "%"));
        }
        cq.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }

    public List<ExtensionEntity> findAllByEraId(ExtensionDTO extension) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ExtensionEntity> cq = cb.createQuery(ExtensionEntity.class);
        Root<ExtensionEntity> root = cq.from(ExtensionEntity.class);
        List<Predicate> predicates = new ArrayList<>();
        Long id = extension.era().id();
        Join<ExtensionEntity, EraEntity> join = root.join("era");
        if (id != null) {
            predicates.add(cb.equal(join.get("id"), id));
        }
        cq.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }

    public ExtensionDTO createNewExtension(ExtensionDTO extension) {
        ExtensionEntity extensionEntity = new ExtensionEntity(extension);
        return ExtensionDTO.makeDTO(extensionRepository.save(extensionEntity));
    }

    public void updateExtension(Long id, String name, List<CardEntity> cards, Integer date, EraEntity era) {
        Optional<ExtensionEntity> extensionEntity = extensionRepository.findById(id);
        extensionEntity.get().setExtName(name);
        extensionEntity.get().setCards(cards);
        extensionEntity.get().setDate(date);
        extensionEntity.get().setEra(era);
        extensionRepository.save(extensionEntity.get());
    }

    public void deleteById(Long id) {
        extensionRepository.deleteById(id);
    }
}
