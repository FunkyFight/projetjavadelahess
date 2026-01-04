package fr.univ.tours.jakartaee.Projet_JEE.controller;

import fr.univ.tours.jakartaee.Projet_JEE.controller.dto.ExtensionDTO;
import fr.univ.tours.jakartaee.Projet_JEE.entities.ExtensionEntity;
import fr.univ.tours.jakartaee.Projet_JEE.repository.ExtensionRepository;
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
@RequestMapping("/extensions")
public class ExtensionController {

    private final ExtensionRepository extensionRepository;

    public ExtensionController(ExtensionRepository extensionRepository){
        this.extensionRepository = extensionRepository;
    }

    @Operation(description = "Shows all extensions/Affiche toutes les extensions",
            responses = @ApiResponse(responseCode = "200", description = "List of extensions/Liste des extensions",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExtensionDTO.class)))))
    @GetMapping
    ResponseEntity<List<ExtensionDTO>> listAll(){
        return ResponseEntity.ok(extensionRepository.findAll().stream().map(ExtensionDTO::new).toList());
    }

    @Operation(description = "Show a specfic extension by its id/Affiche une extension en particulier grâce à son id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Extension found/Extension trouvée",
                            content = @Content(schema = @Schema(implementation = ExtensionDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Extension not found(check id)/Extension non trouvée(vérifiez l'id)", content = @Content(schema = @Schema))
            },
            parameters = @Parameter(name = "id", in = PATH, description = "The extension's id/L'id de la extension")
    )
    @GetMapping("/{id}")
    ResponseEntity<ExtensionDTO> getExtension(@PathVariable Long id){
        return ResponseEntity.of(extensionRepository.findById(id).map(ExtensionDTO::new));
    }

    @Operation(description = "Adding a new extension/Ajout d'une nouvelle extension",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Extension added/Extension ajoutée", headers = @Header(name = "location", description = "URI of the extension/URI de l'extension")),
                    @ApiResponse(responseCode = "400", description = "That extension already exists/Cette extension existe déjà")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New extension/Nouvelle extension", required = true, content = @Content(schema = @Schema(implementation = ExtensionDTO.class)))
    )
    @PostMapping
    ResponseEntity<Void> addExtension(@RequestBody ExtensionDTO extensionDTO){
        if(extensionRepository.existsById(extensionDTO.id())){
            return ResponseEntity.badRequest().build(); /*already exists/existe déjà*/
        }
        /*creating an extension/création d'une extension*/
        ExtensionEntity extensionEntity = new ExtensionEntity(extensionDTO);
        extensionEntity = extensionRepository.save(extensionEntity);
        return ResponseEntity.created(URI.create("/extensions/" + extensionEntity.getId())).build();
    }

    @Operation(description = "Update an extension/Modifier une extension",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Extension updated/Extension modifiée"),
                    @ApiResponse(responseCode = "404", description = "Extension not found (check id)/Extension non trouvée (vérifiez l'id)")
            },
            parameters = @Parameter(name = "id", in = PATH, description = "ID of the extension to update/ID de l'extension à modifier"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Extension to update/Extension à modifier", required = true, content = @Content(schema = @Schema(implementation = ExtensionDTO.class)))
    )
    @PutMapping("/{id}")
    ResponseEntity<Void> updateExtension(@RequestBody ExtensionDTO extensionDTO, @PathVariable String id){
        if(!extensionRepository.existsById(Long.valueOf(id))) {
            return ResponseEntity.notFound().build(); /*doesn't exist/n'existe pas*/
        }
        /*updating infos thanks to extensionDTO/modification des infos grâçe à extensionDTO*/
        ExtensionEntity extensionEntity = new ExtensionEntity(extensionDTO);
        extensionEntity.setId(Long.valueOf(id));
        extensionRepository.save(extensionEntity);
        return ResponseEntity.noContent().location(URI.create("/extensions/" + id)).build();
    }

    @Operation(description = "Delete an extension/Supprimer une extension",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Extension deleted/Extension supprimée"),
                    @ApiResponse(responseCode = "404", description = "Extension not found (check id)/Extension non trouvée (vérifiez l'id)")
            },
            parameters = @Parameter(name = "id", in = PATH, description = "ID of the extension to delete/ID de l'extension' à supprimer")
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteExtension(@PathVariable String id){
        if(!extensionRepository.existsById(Long.valueOf(id))) {
            return ResponseEntity.notFound().build(); /*doesn't exist/n'existe pas*/
        }
        /*deleting the desired ExtensionEntity by  its id/suppression de l'ExtensionEntity souhaité avec son id*/
        extensionRepository.deleteById(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }


}
