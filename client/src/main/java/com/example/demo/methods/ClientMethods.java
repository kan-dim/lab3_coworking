package com.example.demo.methods;


import com.example.demo.classes.people.Client;
import com.example.demo.classes.people.ClientType;
import com.example.demo.classes.people.Language;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class ClientMethods {

    final String address = "http://172.17.0.3:8087/clients/";
    RestTemplate restTempl = new RestTemplate();

    public void createClients() {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("name", "Artem").
                queryParam("language", Language.UA).
                queryParam("ClientType", ClientType.COMPANY);

        System.out.println("Create new clients");

        HttpEntity<Client> response = restTempl.exchange(builder.toUriString(),
                HttpMethod.POST, null, Client.class);

        System.out.println(response.getBody());

        builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("name", "Slava").
                queryParam("language", Language.SP).
                queryParam("ClientType", ClientType.FREELANCER);


        response = restTempl.exchange(builder.toUriString(),
                HttpMethod.POST, null, Client.class);

        System.out.println(response.getBody());
    }

    public void getClientsInfo(String name, MappingState state) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("name", name)
                .queryParam("state", state);

        HttpEntity<String> response = restTempl.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);

        System.out.println(response.getBody());
    }

    public void deleteClientByName(String name) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(address).
                queryParam("name", name);

        HttpEntity<String> response = restTempl.exchange(builder.toUriString(), HttpMethod.DELETE, null, String.class);

        System.out.println(response.getBody());
    }

}