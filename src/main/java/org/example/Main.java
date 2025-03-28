package org.example;
import org.example.cliente.WebClientLoggingConfig;
import org.example.exceptions.ServerErrorException;
import org.example.rest.*;

import org.example.model.Manufacturer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;


public class Main {
    private static final String BASE_URL = "http://localhost:8080/api"; // Cambia el puerto si es necesario

    public static void main(String[] args) {

        ManufacturerService manufacturerService = new ManufacturerService();

        WebClientLoggingConfig webClientLoggingConfig = new WebClientLoggingConfig();

        // Obtener el WebClient configurado con logging
        WebClient webClient = webClientLoggingConfig.webClient();

        try {
            List<Manufacturer> manuf = manufacturerService.getAllManufacturers();
            printManuf(manuf, "Manufacturers:");
        }
        catch(ServerErrorException e)
        {
            System.err.println("ServerErrorException "+e.getMessage());
        }
        catch(RuntimeException e)
        {
            System.err.println("RuntimeException "+e.getMessage());
        }
        try {
            Long numeroDeManuf = manufacturerService.countManufacturers();
            System.out.println(numeroDeManuf);
        }
        catch(ServerErrorException e)
        {
            System.err.println("ServerErrorException "+e.getMessage());
        }
        catch(RuntimeException e)
        {
            System.err.println("RuntimeException "+e.getMessage());
        }

System.exit(0);
        // a continuación llamadas al servidor "directas", esto es, sin usar MAnufacturerClient
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


