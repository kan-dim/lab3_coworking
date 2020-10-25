package com.example.demo.methods;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class ExcursionMethods {

    final String address = "http://172.17.0.3:8087/excursions/";
    RestTemplate restTempl = new RestTemplate();
    public void createExcursion() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("guideName", "Orange").
                queryParam("day", 5).
                queryParam("visitors", 10).
                queryParam("duration", 100).
                queryParam("month", 10);

        System.out.println("Create new Excursion");

        HttpEntity<String> response = restTempl.exchange(builder.toUriString(),
                HttpMethod.POST, null, String.class);

        System.out.println(response.getBody());

        builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("guideName", "Apple").
                queryParam("day", 7).
                queryParam("visitors", 12).
                queryParam("duration", 125).
                queryParam("month", 3);


        response = restTempl.exchange(builder.toUriString(),
                HttpMethod.POST, null, String.class);

        System.out.println(response.getBody());
    }

    public void getExcursionInfo(String name, MappingState state) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("name", name)
                .queryParam("state", state);

        HttpEntity<String> response = restTempl.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);

        System.out.println(response.getBody());
    }

    public void deleteExcursionByName(String name) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("name", name);

        HttpEntity<String> response = restTempl.exchange(builder.toUriString(), HttpMethod.DELETE, null, String.class);

        System.out.println(response.getBody());
    }

}