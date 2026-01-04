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

    @Operation(description = "Shows all eras/Affiche toutes les ères", responses = @ApiResponse(responseCode = "200", description = "List of eras/Liste des ères", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EraDTO.class)))))
    @GetMapping
    ResponseEntity<List<EraDTO>> listAll() {
        return ResponseEntity.ok(eraRepository.findAll().stream().map(EraDTO::new).toList());
    }

    @Operation(description = "Show a specfic era by its id/Affiche une ère en particulier grâce à son id", responses = {
            @ApiResponse(responseCode = "200", description = "Era found/Ere trouvée", content = @Content(schema = @Schema(implementation = CardDTO.class))),
            @ApiResponse(responseCode = "404", description = "Era not found(check id)/Ere non trouvée(vérifiez l'id)", content = @Content(schema = @Schema))
    }, parameters = @Parameter(name = "id", in = PATH, description = "The era's id/L'id de l'ére"))
    @GetMapping("/{id}")
    ResponseEntity<EraDTO> getEra(@PathVariable Long id) {
        return ResponseEntity.of(eraRepository.findById(id).map(EraDTO::new));
    }

    @Operation(description = "Adding a new era/Ajout d'une nouvelle ère", responses = {
            @ApiResponse(responseCode = "201", description = "Era added/Ere ajoutée", headers = @Header(name = "location", description = "URI of the era/URI de l'ère")),
            @ApiResponse(responseCode = "400", description = "That era already exists/Cette ère existe déjà")
    }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New era/Nouvelle ère", required = true, content = @Content(schema = @Schema(implementation = EraDTO.class))))
    @PostMapping
    ResponseEntity<Void> addEra(@RequestBody EraDTO eraDTO) {
        EraEntity eraEntity = new EraEntity(eraDTO);
        eraEntity = eraRepository.save(eraEntity);
        return ResponseEntity.created(URI.create("/eras/" + eraEntity.getId())).build();
    }

    @Operation(description = "Update an era/Modifier une ère", responses = {
            @ApiResponse(responseCode = "204", description = "Era updated/Ere modifiée"),
            @ApiResponse(responseCode = "404", description = "Era not found (check id)/Ere non trouvée (vérifiez l'id)")
    }, parameters = @Parameter(name = "id", in = PATH, description = "ID of the era to update/ID de l'ère à modifier"), requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Era to update/Ere à modifier", required = true, content = @Content(schema = @Schema(implementation = EraDTO.class))))
    @PutMapping("/{id}")
    ResponseEntity<Void> updateEra(@RequestBody EraDTO eraDTO, @PathVariable String id) {
        if (!eraRepository.existsById(Long.valueOf(id))) {
            return ResponseEntity.notFound().build();
        }

        EraEntity eraEntity = new EraEntity(eraDTO);
        eraEntity.setId(Long.valueOf(id));
        eraRepository.save(eraEntity);
        return ResponseEntity.noContent().location(URI.create("/eras/" + id)).build();
    }

    @Operation(description = "Delete an era/Supprimer une ère", responses = {
            @ApiResponse(responseCode = "204", description = "Era deleted/Ere supprimée"),
            @ApiResponse(responseCode = "404", description = "Era not found (check id)/Ere non trouvée (vérifiez l'id)")
    }, parameters = @Parameter(name = "id", in = PATH, description = "ID of the era to delete/ID de l'ère' à supprimer"))
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteEra(@PathVariable String id) {
        if (!eraRepository.existsById(Long.valueOf(id))) {
            return ResponseEntity.notFound().build();
        }

        eraRepository.deleteById(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }
}
