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
@RequestMapping(value = "/groups")
public class GroupController {

    private final RestTemplate template = new RestTemplate();
    private final String address = "http://groupService:8086/groups/";

    @GetMapping
    public ResponseEntity<String> excursionInfo(@RequestParam UUID id, @RequestParam MappingState state) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("state", state).
                queryParam("id", id);
        HttpEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);


        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping
    public ResponseEntity<String> addNewGroup(@RequestParam int day, @RequestParam int month,
                                              @RequestParam String clientsList, @RequestParam UUID managerId,
                                              @RequestParam UUID excursionId) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("day", day).
                queryParam("month", month).
                queryParam("managerId", managerId).
                queryParam("excursionId", excursionId).
                queryParam("clientsList", clientsList);
        HttpEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.POST, null, String.class);


        return ResponseEntity.ok(response.getBody());
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteGroupByDId(@RequestParam UUID id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("id", id);

        HttpEntity<Boolean> response = template.exchange(builder.toUriString(), HttpMethod.DELETE, null, Boolean.class);

        return ResponseEntity.ok(response.getBody());
    }
}