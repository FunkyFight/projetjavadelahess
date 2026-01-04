package fr.univ.tours.jakartaee.Projet_JEE_Frontend.consumer;

import fr.univ.tours.jakartaee.Projet_JEE_Frontend.dto.CardDTO;
import fr.univ.tours.jakartaee.Projet_JEE_Frontend.models.Card;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class CardConsumer {

    private final RestClient client;

    public CardConsumer(@Value("${projet.jee.backend.url}") String backendUrl) {
        this.client = RestClient.create(backendUrl);
    }

    public List<CardDTO> searchCards(Card card) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/cards/search");
        if (card.getId() != null) {
            builder.queryParam("id", card.getId());
        }
        if (card.getCardName() != null && !card.getCardName().isBlank()) {
            builder.queryParam("name", card.getCardName());
        }
        if (card.getExtension() != null) {
            builder.queryParam("extension", card.getExtension());
        }
        if (card.getComment() != null && !card.getComment().isBlank()) {
            builder.queryParam("comment", card.getComment());
        }
        String uri = builder.toUriString();
        return client.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public Card getCardById(String id){ /*bien que l'id de carte soit en Long, je juge préférable de le mettre en String pour faciliter la manipulation*/
        return client.get().uri("/cards/{id}", id).retrieve().onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
            throw new RuntimeException("4xx error");
        }).body(Card.class);
    }

    public List<Card> getAllCards() {
        return client.get().uri("/cards").retrieve().body(new ParameterizedTypeReference<>() {
        });
    }

    public void createCard(Card card) {
        client.post().uri("/cards").body(card).retrieve().body(Card.class);
    }

    public void updateCard(String id, Card card) { /*id est en String ici aussi pour la même raison*/
        client.put().uri("/cards/{id}", id).body(card).retrieve().body(Card.class);
    }

}
