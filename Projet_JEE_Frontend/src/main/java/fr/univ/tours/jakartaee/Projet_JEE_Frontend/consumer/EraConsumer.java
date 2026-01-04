package fr.univ.tours.jakartaee.Projet_JEE_Frontend.consumer;

import fr.univ.tours.jakartaee.Projet_JEE_Frontend.models.Era;
import fr.univ.tours.jakartaee.Projet_JEE_Frontend.models.Extension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class EraConsumer {

    private final RestClient client;

    public EraConsumer(@Value("${projet.jee.backend.url}") String backendUrl) {
        this.client = RestClient.create(backendUrl);
    }

    public Era getEraById(String id){
        return client.get().uri("/eras/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {throw new RuntimeException("4xx error");})
                .body(Era.class);
    }

    public List<Era> getAllEras() {
        return client.get().uri("/eras").retrieve().body(new ParameterizedTypeReference<>() {
        });
    }

    public void createEra(Era era) {
        client.post().uri("/eras").body(era).retrieve().body(Era.class);
    }
}
