package org.example.cliente;
import org.example.model.*;

import org.example.exceptions.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class ManufacturerClient {

    private final WebClient webClient;

    public ManufacturerClient() {
        // Crear el WebClient manualmente sin usar @Bean o configuraciones de Spring
      //  this.webClient = WebClient.create("http://localhost:8080");  // Aquí va la URL de tu API

        WebClientLoggingConfig webClientLoggingConfig = new WebClientLoggingConfig();

        // Obtener el WebClient configurado con logging
        this.webClient = webClientLoggingConfig.webClient();
    }

    public List<Manufacturer> getAllManufacturers() {
        List<Manufacturer> manufacturers=null;
        try {
             manufacturers  = webClient.get()
                    .uri("/manufacturers")
                    .retrieve()

                    .bodyToFlux(Manufacturer.class)  // Convertimos la respuesta en una lista de Manufacturer

                    .collectList()  // Convertimos el Flux a un Mono con la lista
                    .block();

        }
        catch(org.springframework.web.reactive.function.client.WebClientRequestException e) {
            throw new ServerErrorException("servidor caido");
        } catch (RuntimeException e) {
            throw new RuntimeException( e.getMessage() ); // Error 500, 400
        }

        return manufacturers;
    }
    public Long countManufacturers() {
        Long manufacturers= 0L;
        try {
            manufacturers  = webClient.get()
                    .uri("/count/{id}",1)
                    .retrieve()

                    .bodyToMono(Long.class) // Convertimos la respuesta en una lista de Manufacturer


                    .block();

        }
        catch(org.springframework.web.reactive.function.client.WebClientRequestException e) {
            throw new ServerErrorException("servidor caido");
        } catch (RuntimeException e) {
            throw new RuntimeException( e.getMessage() ); // Error 500, 400
        }

        return manufacturers;
    }


}



