package fr.univ.tours.jakartaee.Projet_JEE.repository;

import fr.univ.tours.jakartaee.Projet_JEE.entities.ExtensionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtensionRepository extends JpaRepository<ExtensionEntity, Long> {
    ExtensionEntity findByExtname(String extension);
    List<ExtensionEntity> findAllByEra(String era);
}