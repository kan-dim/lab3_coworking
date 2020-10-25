package com.example.demo.methods;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class GroupMethods {

    final String address = "http://172.17.0.3:8087/groups/";
    RestTemplate restTempl = new RestTemplate();


    public void createGroup() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("day", 11).
                queryParam("clientsList", "VLAD").
                queryParam("managerName", "Persik").
                queryParam("month", 12);

        System.out.println("Create new groups");

        HttpEntity<String> response = restTempl.exchange(builder.toUriString(),
                HttpMethod.POST, null, String.class);

        System.out.println(response.getBody());
    }

    public void getGroupInfo(int day, int month, MappingState state) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("month", day).
                queryParam("day", month).
                queryParam("state", state);

        HttpEntity<String> response = restTempl.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);

        System.out.println(response.getBody());
    }

    public void deleteGroupByDate(int day, int month) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("month", day).
                queryParam("day", month);

        HttpEntity<String> response = restTempl.exchange(builder.toUriString(), HttpMethod.DELETE, null, String.class);

        System.out.println(response.getBody());
    }

}