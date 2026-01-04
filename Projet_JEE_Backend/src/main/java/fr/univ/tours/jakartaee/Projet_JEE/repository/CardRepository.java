package fr.univ.tours.jakartaee.Projet_JEE.repository;

import fr.univ.tours.jakartaee.Projet_JEE.entities.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
    List<CardEntity> findAll();
    List<CardEntity> findAllByExtension(String extension);
    List<CardEntity> findAllByExtensionId(Long id);
    CardEntity findByCardName(String name);
}
