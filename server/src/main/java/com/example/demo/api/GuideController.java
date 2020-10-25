package com.example.demo.api;

import com.example.demo.classes.MappingState;
import com.example.demo.classes.people.Guide;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping(value = "/guides")
public class GuideController {

    private final RestTemplate template = new RestTemplate();
    private final String address = "http://guideService:8084/guides/";

    @GetMapping
    public ResponseEntity<String> guiderInfo(@RequestParam String name, @RequestParam MappingState state) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("state", state).
                queryParam("name", name);
        HttpEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);


        return ResponseEntity.ok(response.getBody());
    }



    @PostMapping
    public ResponseEntity<Guide> addNewGuide(@RequestParam String name, @RequestParam boolean knowEnglish) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("knowEnglish", knowEnglish).
                queryParam("name", name);
        HttpEntity<Guide> response = template.exchange(builder.toUriString(), HttpMethod.POST, null, Guide.class);


        return ResponseEntity.ok(response.getBody());
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteManagerByName(@RequestParam String name) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("name", name);

        HttpEntity<Boolean> response = template.exchange(builder.toUriString(), HttpMethod.DELETE, null, Boolean.class);

        return ResponseEntity.ok(response.getBody());
    }

    @PutMapping
    public ResponseEntity<Boolean> toggleFreeGuideState(@RequestParam UUID id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("id", id);

        HttpEntity<Boolean> response = template.exchange(builder.toUriString(), HttpMethod.PUT, null, Boolean.class);

        return ResponseEntity.ok(response.getBody());
    }


}