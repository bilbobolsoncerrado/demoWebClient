package org.example;
import javafx.application.Platform;
import org.example.cliente.WebClientLoggingConfig;
import org.example.rest.*;
import org.example.exceptions.*;
import org.example.model.Manufacturer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {
    private static final String BASE_URL = "http://localhost:8080/api"; // Cambia el puerto si es necesario

    public static void main(String[] args) {

        ManufacturerService manufacturerService = new ManufacturerService();

        WebClientLoggingConfig webClientLoggingConfig = new WebClientLoggingConfig();

        // Obtener el WebClient configurado con logging
        WebClient webClient = webClientLoggingConfig.webClient();

        try {
            // Realiza la solicitud de manera síncrona
            List<Manufacturer> manufacturers  = webClient.get()
                    .uri("/manufacturers")  // Cambia esta URI según tu servidor
                    .retrieve()
                    .bodyToFlux(Manufacturer.class)
                    .collectList()
                    .block();  // Bloquea para esperar la respuesta


        } catch (org.springframework.web.reactive.function.client.WebClientRequestException e) {
            System.err.println("❌ Error de conexión: "); // Servidor caido sale por aqui
        } catch (Exception e) {
            System.err.println("❌ Otro error: " + e.getMessage());
        }

        List<Manufacturer> manuf= manufacturerService.getAllManufacturers();
        printManuf(manuf,"Manufacturers:");
System.exit(0);

                // Realizar petición GET a "/api/manufacturers"
                List<Manufacturer> manufacturers = webClient.get()
                        .uri("/manufacturers")
                        .retrieve()
                        .bodyToFlux(Manufacturer.class)
                        .collectList()
                        .block();
                printManuf(manufacturers,"Manufacturers:");

                // Realizar petición GET a "/api/manufacturers/year/1990"
                List<Manufacturer> manufacturersByYear = webClient.get()
                        .uri("/manufacturers/year/{year}", 1990)
                        .retrieve()
                        .bodyToFlux(Manufacturer.class)
                        .collectList()
                        .block();
                printManuf(manufacturersByYear,"Manufacturers from year 1990: ");

                // Realizar petición GET a "/api/manufacturers?year=1990"
                List<Manufacturer> manufacturersByYearWithRequest= webClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/manufacturers")
                        .queryParam("year", 1990)
                        .build())
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<List<Manufacturer>>() {})
                        .block();
                printManuf(manufacturersByYearWithRequest,"Manufacturers from year 1990 con Request: ");

                // Realizar petición GET a "/api/manufacturers?year=1990"
                List<Manufacturer> manufacturersByYearWithRequest2=webClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/manufacturers")
                        .queryParam("year", 1990)
                        .build())
                        .retrieve()
                        .bodyToFlux(Manufacturer.class)
                        .collectList()
                        .block();
                printManuf(manufacturersByYearWithRequest2,"Manufacturers from year 1990 con Request: ");

                // Manejando fechas
                Date now = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String formattedDate = formatter.format(now);
                List<Manufacturer> manufacturersByInitialDate = webClient.get()
                        .uri("/manufacturers/initialDate/{initialDate}", formattedDate)
                        .retrieve()
                        .bodyToFlux(Manufacturer.class)
                        .collectList()
                        .block();
                printManuf(manufacturersByInitialDate,"Manufacturers de hoy:");
//
            }
            public static void printManuf( List<Manufacturer> mannufacturers, String mensaje)
            {
                if (mannufacturers != null && !mannufacturers.isEmpty()) {
                    System.out.println(mensaje+" ");
                    mannufacturers.forEach(manufacturer -> System.out.println(manufacturer));
                } else {
                    System.out.println("No manufacturers found hoy.");
                }
            }
        }


