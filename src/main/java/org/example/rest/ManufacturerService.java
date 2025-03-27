package org.example.rest;

import org.example.model.Manufacturer;
import org.example.cliente.*;
import java.util.List;

import reactor.core.publisher.Mono;
import java.util.List;

public class ManufacturerService {

    private final ManufacturerClient manufacturerClient;

    public ManufacturerService() {
        this.manufacturerClient = new ManufacturerClient();  // Crear el cliente sin @Autowired
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerClient.getAllManufacturers();
    }

}


