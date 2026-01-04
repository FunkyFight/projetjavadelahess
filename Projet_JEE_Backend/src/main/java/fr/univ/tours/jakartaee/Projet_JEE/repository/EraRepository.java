package fr.univ.tours.jakartaee.Projet_JEE.repository;

import fr.univ.tours.jakartaee.Projet_JEE.entities.EraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EraRepository extends JpaRepository<EraEntity, Long> {
    EraEntity findByEraname(String era);
}
