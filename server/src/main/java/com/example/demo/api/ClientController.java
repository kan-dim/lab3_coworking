package com.example.demo.api;

import com.example.demo.classes.MappingState;
import com.example.demo.classes.people.Client;
import com.example.demo.classes.people.ClientType;
import com.example.demo.classes.people.Language;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {
    private final RestTemplate template = new RestTemplate();
    private final String address = "http://clientService:8083/clients/";

    @GetMapping
    public ResponseEntity<String> clientsInfo(@RequestParam String name, @RequestParam MappingState state) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("state", state).
                queryParam("name", name);
        HttpEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);


        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping
    public ResponseEntity<Client> addClient(@RequestParam String name, @RequestParam Language language, ClientType type) {


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("language", language).
                queryParam("type", type).
                queryParam("name", name);
        HttpEntity<Client> response = template.exchange(builder.toUriString(), HttpMethod.POST, null, Client.class);


        return ResponseEntity.ok(response.getBody());
    }



    @DeleteMapping
    public ResponseEntity<Boolean> deleteClientByName(@RequestParam String name) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("name", name);

        HttpEntity<Boolean> response = template.exchange(builder.toUriString(), HttpMethod.DELETE, null, Boolean.class);

        return ResponseEntity.ok(response.getBody());
    }
}