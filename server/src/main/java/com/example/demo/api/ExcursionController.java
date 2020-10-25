package com.example.demo.api;

import com.example.demo.classes.MappingState;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;


@RestController
@RequestMapping(value = "/excursions")
public class ExcursionController {

    private final RestTemplate template = new RestTemplate();
    private final String address = "http://excursionService:8085/excursions/";

    @GetMapping
    public ResponseEntity<String> excursionInfo(@RequestParam UUID id, @RequestParam MappingState state) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("state", state).
                queryParam("id", id);
        HttpEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);


        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping
    public ResponseEntity<String> addNewExcursion( @RequestParam int day, @RequestParam int month,
                                              @RequestParam int duration, @RequestParam int visitors,
                                              @RequestParam String guideName) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("day", day).
                queryParam("month", month).
                queryParam("duration", duration).
                queryParam("visitors", visitors).
                queryParam("guideName", guideName);
        HttpEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.POST, null, String.class);


        return ResponseEntity.ok(response.getBody());
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteExcursionById(@RequestParam UUID id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("id", id);

        HttpEntity<Boolean> response = template.exchange(builder.toUriString(), HttpMethod.DELETE, null, Boolean.class);

        return ResponseEntity.ok(response.getBody());
    }
}