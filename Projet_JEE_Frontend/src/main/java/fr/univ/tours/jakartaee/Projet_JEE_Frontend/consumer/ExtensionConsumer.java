package fr.univ.tours.jakartaee.Projet_JEE_Frontend.consumer;

import fr.univ.tours.jakartaee.Projet_JEE_Frontend.models.Extension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class ExtensionConsumer {

    private final RestClient client;

    public ExtensionConsumer(@Value("${projet.jee.backend.url}") String backendUrl) {
        this.client = RestClient.create(backendUrl);
    }

    public Extension getExtensionById(String id){
        return client.get().uri("/extensions/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {throw new RuntimeException("4xx error");})
                .body(Extension.class);
    }

    public List<Extension> getAllExtensions() {
        return client.get().uri("/extensions").retrieve().body(new ParameterizedTypeReference<>() {
        });
    }

    public void createExtension(Extension extension) {
        client.post().uri("/extensions").body(extension).retrieve().body(Extension.class);
    }

}
