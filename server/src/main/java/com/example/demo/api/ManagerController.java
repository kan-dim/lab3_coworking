package com.example.demo.api;
import com.example.demo.classes.people.Manager;
import com.example.demo.classes.MappingState;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/managers")
public class ManagerController {

    private final RestTemplate template = new RestTemplate();
    private final String address = "http://managerService:8082/managers/";

    @GetMapping
    public ResponseEntity<String> managersInfo(@RequestParam String name, @RequestParam MappingState state) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("state", state).
                queryParam("name", name);
        HttpEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);


        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping
    public ResponseEntity<Manager> addManager(@RequestParam String name, @RequestParam boolean knowEnglish) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("knowEnglish", knowEnglish).
                queryParam("name", name);
        HttpEntity<Manager> response = template.exchange(builder.toUriString(), HttpMethod.POST, null, Manager.class);


        return ResponseEntity.ok(response.getBody());
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteManagerByName(@RequestParam String name) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("name", name);

        HttpEntity<Boolean> response = template.exchange(builder.toUriString(), HttpMethod.DELETE, null, Boolean.class);

        return ResponseEntity.ok(response.getBody());
    }
}