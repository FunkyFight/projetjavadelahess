package fr.univ.tours.jakartaee.Projet_JEE.services;


import fr.univ.tours.jakartaee.Projet_JEE.controller.dto.EraDTO;
import fr.univ.tours.jakartaee.Projet_JEE.controller.dto.ExtensionDTO;
import fr.univ.tours.jakartaee.Projet_JEE.entities.EraEntity;
import fr.univ.tours.jakartaee.Projet_JEE.entities.ExtensionEntity;
import fr.univ.tours.jakartaee.Projet_JEE.repository.EraRepository;
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
public class EraService {

    private final EraRepository eraRepository;
    private final ExtensionRepository extensionRepository;
    private EntityManager em;

    public EraService(EraRepository eraRepository, ExtensionRepository extensionRepository) {
        this.eraRepository = eraRepository;
        this.extensionRepository = extensionRepository;
    }

    public List<EraEntity> findAllByEraId(EraDTO era) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EraEntity> cq = cb.createQuery(EraEntity.class);
        Root<EraEntity> root = cq.from(EraEntity.class);
        List<Predicate> predicates = new ArrayList<>();
        Long id = era.id();
        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }
        cq.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }

    public List<EraEntity> findAllByEraName(EraDTO era) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EraEntity> cq = cb.createQuery(EraEntity.class);
        Root<EraEntity> root = cq.from(EraEntity.class);
        List<Predicate> predicates = new ArrayList<>();
        String name = era.eraname();
        if (name != null && !name.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("eraname")), "%" + name.toLowerCase() + "%"));
        }
        cq.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }

    public EraDTO createNewEra(EraDTO era) {
        EraEntity eraEntity = new EraEntity(era);
        return EraDTO.makeDTO(eraRepository.save(eraEntity));
    }

    public void updateEra(Long id, String name, List<ExtensionEntity> extensions) {
        Optional<EraEntity> eraEntity = eraRepository.findById(id);
        eraEntity.get().setEraName(name);
        eraEntity.get().setExtensions(extensions);
        eraRepository.save(eraEntity.get());
    }

    public void deleteById(Long id) {
        eraRepository.deleteById(id);
    }
}
