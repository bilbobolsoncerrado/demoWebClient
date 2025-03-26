package org.example;


import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.ResponseEntity;
import java.util.List;
public class Main {
    private static final String BASE_URL = "http://localhost:8080/api"; // Cambia el puerto si es necesario

    public static void main(String[] args) {


                WebClient webClient = WebClient.create(BASE_URL);

                // Realizar petición GET a "/api/manufacturers"
                List<Manufacturer> manufacturers = webClient.get()
                        .uri("/manufacturers")
                        .retrieve()
                        .bodyToFlux(Manufacturer.class)
                        .collectList()
                        .block();

                if (manufacturers != null && !manufacturers.isEmpty()) {
                    System.out.println("Manufacturers: ");
                    manufacturers.forEach(manufacturer -> System.out.println(manufacturer));
                } else {
                    System.out.println("No manufacturers found.");
                }

                // Realizar petición GET a "/api/manufacturers/year/1990"
                List<Manufacturer> manufacturersByYear = webClient.get()
                        .uri("/manufacturers/year/{year}", 1990)
                        .retrieve()
                        .bodyToFlux(Manufacturer.class)
                        .collectList()
                        .block();

                if (manufacturersByYear != null && !manufacturersByYear.isEmpty()) {
                    System.out.println("Manufacturers from year 1990: ");
                    manufacturersByYear.forEach(manufacturer -> System.out.println(manufacturer));
                } else {
                    System.out.println("No manufacturers found from year 1990.");
                }
            }
        }


