package com.example.demo.classes.people;

import java.util.UUID;


public class Client {

    private  String name;
    private  ClientType clientType;
    private  Language language;
    private UUID id;


    public Client(String name, ClientType clientType, Language language) {
        this.name = name;
        this.clientType = clientType;
        this.language = language;
        this.id = UUID.randomUUID();
    }

    public Client() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ("\n\t ---- CLIENT ----" +
                "\n\tИмя : " + name + "\n\tТип: " + clientType +
                "\n\tЯзык: " + language + "\n\tID: " + id + "\n");
    }
}
