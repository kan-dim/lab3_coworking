package com.example.demo.api;

import com.example.demo.classes.service.ExcursionService;
import com.example.demo.classes.MappingState;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final ExcursionService service;
    private final RestTemplate template = new RestTemplate();

    @Autowired
    public ExcursionController(ExcursionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<String> excursionInfo(@RequestParam UUID id, @RequestParam MappingState state) {
        if (state == MappingState.all) return ResponseEntity.ok(service.excursionsInfo());
        return ResponseEntity.ok(service.getExcursionById(id));
    }

    @PostMapping
    public ResponseEntity<String> addNewExcursion( @RequestParam int day, @RequestParam int month,
                                              @RequestParam int duration, @RequestParam int visitors,
                                              @RequestParam String guideName) {

        String address = "http://localhost:8084/guides/";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("state", MappingState.byName).
                queryParam("name", guideName);


        HttpEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);
        UUID guideId = UUID.fromString(response.getBody());

        builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("id", guideId);

        // toggle guide state
        HttpEntity<Boolean> resp = template.exchange(builder.toUriString(), HttpMethod.PUT, null, Boolean.class);

        return ResponseEntity.ok(service.addNewExcursion( day, month, duration, visitors, guideId, guideName));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteExcursionById(@RequestParam UUID id) {
        return ResponseEntity.ok(service.deleteExcursionById(id));
    }
}