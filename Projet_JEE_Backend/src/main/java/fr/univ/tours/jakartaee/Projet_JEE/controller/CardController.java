package fr.univ.tours.jakartaee.Projet_JEE.controller;

import fr.univ.tours.jakartaee.Projet_JEE.controller.dto.CardDTO;
import fr.univ.tours.jakartaee.Projet_JEE.controller.dto.ExtensionDTO;
import fr.univ.tours.jakartaee.Projet_JEE.entities.CardEntity;
import fr.univ.tours.jakartaee.Projet_JEE.repository.CardRepository;
import fr.univ.tours.jakartaee.Projet_JEE.services.CardService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.headers.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.net.URI;
import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardRepository cardRepository;
    private final CardService cardService;

    public CardController(CardRepository cardRepository, CardService cardService){
        this.cardRepository = cardRepository;
        this.cardService = cardService;
    }

    @Operation(description = "Shows all cards/Affiche toutes les cartes",
            responses = @ApiResponse(responseCode = "200", description = "List of cards/Liste des cartes",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CardDTO.class)))))
    @GetMapping
    ResponseEntity<List<CardDTO>> listAll(){
        return ResponseEntity.ok(cardRepository.findAll().stream().map(CardDTO::new).toList());
    }

    @Operation(description = "Show a specfic card by its id/Affiche une carte en particulier grâce à son id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Card found/Carte trouvée",
                            content = @Content(schema = @Schema(implementation = CardDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Card not found(check id)/Carte non trouvée(vérifiez l'id)", content = @Content(schema = @Schema))
            },
            parameters = @Parameter(name = "id", in = PATH, description = "The card's id/L'id de la carte")
    )
    @GetMapping("/{id}")
    ResponseEntity<CardDTO> getCard(@PathVariable Long id){
        return ResponseEntity.of(cardRepository.findById(id).map(CardDTO::new));
    }

        @Operation(description = "Show cards according to their attributes/Affiche des carte en fonction de leurs attributs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cards found/Cartes trouvées",
                            content = @Content(schema = @Schema(implementation = CardDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Cards not found(no such cards)/Cartes non trouvées(pas de résultats)", content = @Content(schema = @Schema))
            },
            parameters = @Parameter(name = "id", in = PATH, description = "The card's id/L'id de la carte")
    )
    @GetMapping("/search")
    public ResponseEntity<List<CardEntity>> searchCardsGet(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) ExtensionDTO extension,
            @RequestParam(required = false) String comment) {

        CardDTO cardDTO = new CardDTO(id, name, extension, comment);
        List<CardEntity> results = cardService.searchCard(cardDTO);
        return ResponseEntity.ok(results);
    }

    @Operation(description = "Adding a new card/Ajout d'une nouvelle carte",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Card added/Carte ajoutée", headers = @Header(name = "location", description = "URI of the card/URI de la carte")),
                    @ApiResponse(responseCode = "400", description = "That card already exists/Cette carte existe déjà")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New card/Nouvelle carte", required = true, content = @Content(schema = @Schema(implementation = CardDTO.class)))
    )
    @PostMapping
    ResponseEntity<Void> addCard(@RequestBody CardDTO cardDTO){
        if(cardRepository.existsById(cardDTO.id())){
            return ResponseEntity.badRequest().build(); /*already exists/existe déjà*/
        }
        /*creating a card/création d'une carte*/
        CardEntity cardEntity = new CardEntity(cardDTO);
        cardEntity = cardRepository.save(cardEntity);
        return ResponseEntity.created(URI.create("/cards/" + cardEntity.getId())).build();
    }

    @Operation(description = "Update a card/Modifier une carte",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Card updated/Carte modifiée"),
                    @ApiResponse(responseCode = "404", description = "Card not found (check id)/Carte non trouvée (vérifiez l'id)")
            },
            parameters = @Parameter(name = "cardDTO", in = PATH, description = "The card's new informations/Les nouvelles informations de la carte"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Card to update/Carte à modifier", required = true, content = @Content(schema = @Schema(implementation = CardDTO.class)))
    )
    @PutMapping("/{id}")
    ResponseEntity<Void> updateCard(@RequestBody CardDTO cardDTO, @PathVariable String id){
        if(!cardRepository.existsById(Long.valueOf(id))) {
            return ResponseEntity.notFound().build(); /*doesn't exist/n'existe pas*/
        }
        /*updating infos thanks to cardDTO/modification des infos grâçe à cardDTO*/
        CardEntity cardEntity = new CardEntity(cardDTO);
        cardEntity.setId(Long.valueOf(id));
        cardRepository.save(cardEntity);
        return ResponseEntity.noContent().location(URI.create("/cards/" + id)).build();
    }

    @Operation(description = "Delete an card/Supprimer une carte",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Card deleted/Carte supprimée"),
                    @ApiResponse(responseCode = "404", description = "Card not found (check id)/Carte non trouvée (vérifiez l'id)")
            },
            parameters = @Parameter(name = "id", in = PATH, description = "ID of the card to delete/ID de la carte' à supprimer")
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCard(@PathVariable String id){
        if(!cardRepository.existsById(Long.valueOf(id))) {
            return ResponseEntity.notFound().build(); /*doesn't exist/n'existe pas*/
        }
        /*deleting the desired CardEntity by its id/suppression de la CardEntity souhaité avec son id*/
        cardRepository.deleteById(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }

}
