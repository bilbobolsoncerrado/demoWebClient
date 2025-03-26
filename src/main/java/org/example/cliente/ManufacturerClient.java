package org.example.cliente;
import org.example.model.*;
import org.example.exceptions.*;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import java.util.List;

public class ManufacturerClient {

    private final WebClient webClient;

    public ManufacturerClient() {
        // Crear el WebClient manualmente sin usar @Bean o configuraciones de Spring
        this.webClient = WebClient.create("http://localhost:8080");  // Aquí va la URL de tu API
    }

    public Mono<List<Manufacturer>> getAllManufacturers() {
        return webClient.get()
                .uri("/api/manufacturers")
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), clientResponse -> {
                    // Manejo de errores 4xx (Errores de cliente)
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new ClientErrorException("Client Error: " + body)));
                })
                .onStatus(status -> status.is5xxServerError(), clientResponse -> {
                    // Manejo de errores 5xx (Errores de servidor)
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new ServerErrorException("Server Error: " + body)));
                })
                .onStatus(status -> status.isError(), clientResponse -> {
                    // Para otros errores generales
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new GeneralErrorException("General Error: " + body)));
                })
                .bodyToFlux(Manufacturer.class)  // Convertimos la respuesta en una lista de Manufacturer
                .collectList();  // Convertimos el Flux a un Mono con la lista
    }
}

