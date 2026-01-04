package fr.univ.tours.jakartaee.Projet_JEE.controller;

import fr.univ.tours.jakartaee.Projet_JEE.controller.dto.CardDTO;
import fr.univ.tours.jakartaee.Projet_JEE.controller.dto.EraDTO;
import fr.univ.tours.jakartaee.Projet_JEE.entities.EraEntity;
import fr.univ.tours.jakartaee.Projet_JEE.repository.EraRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;

@RestController
@RequestMapping("/eras")
public class EraController {

    private final EraRepository eraRepository;

    public EraController(EraRepository eraRepository) {
        this.eraRepository = eraRepository;
    }

    @Operation(description = "Shows all eras", responses = @ApiResponse(responseCode = "200", description = "List of eras"))
    @GetMapping
    public ResponseEntity<List<EraDTO>> listAll() {
        return ResponseEntity.ok(eraRepository.findAll().stream().map(EraDTO::new).toList());
    }

    @Operation(description = "Show an era by id", responses = {
            @ApiResponse(responseCode = "200", description = "Era found"),
            @ApiResponse(responseCode = "404", description = "Era not found")
    }, parameters = @Parameter(name = "id", in = PATH, description = "Era ID"))
    @GetMapping("/{id}")
    public ResponseEntity<EraDTO> getEra(@PathVariable Long id) {
        return ResponseEntity.of(eraRepository.findById(id).map(EraDTO::new));
    }

    @Operation(description = "Add a new era", responses = {
            @ApiResponse(responseCode = "201", description = "Era added"),
            @ApiResponse(responseCode = "400", description = "Error")
    })
    @PostMapping
    public ResponseEntity<EraDTO> addEra(@RequestBody EraDTO eraDTO) {
        System.out.println("DEBUG: addEra called with " + eraDTO);
        EraEntity eraEntity = new EraEntity(eraDTO);
        eraEntity = eraRepository.save(eraEntity);
        return ResponseEntity.created(URI.create("/eras/" + eraEntity.getId())).body(new EraDTO(eraEntity));
    }

    @Operation(description = "Update an era", responses = {
            @ApiResponse(responseCode = "204", description = "Updated"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEra(@RequestBody EraDTO eraDTO, @PathVariable String id) {
        if (!eraRepository.existsById(Long.valueOf(id))) {
            return ResponseEntity.notFound().build();
        }
        EraEntity eraEntity = new EraEntity(eraDTO);
        eraEntity.setId(Long.valueOf(id));
        eraRepository.save(eraEntity);
        return ResponseEntity.noContent().location(URI.create("/eras/" + id)).build();
    }

    @Operation(description = "Delete an era")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEra(@PathVariable String id) {
        if (!eraRepository.existsById(Long.valueOf(id))) {
            return ResponseEntity.notFound().build();
        }
        eraRepository.deleteById(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }
}
