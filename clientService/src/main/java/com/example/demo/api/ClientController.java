package com.example.demo.api;

import com.example.demo.classes.service.ClientService;
import com.example.demo.classes.people.ClientType;
import com.example.demo.classes.people.Language;
import com.example.demo.config.MessagingConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClientController {

    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public Object handleRequest(String request) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(request, Map.class);

        String jsonString = null;

        try {
            jsonString = new JSONObject(map.get("body").toString()).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Map<String, Object> body = mapper.readValue(jsonString, Map.class);

        String method = (String) map.get("method");
        return switch (method) {
            case "all" -> service.clientInfo();
            case "get" -> service.getClientByName((String) body.get("name"));
            case "post" -> service.addNewClient((String) body.get("name"),Language.valueOf((String) body.get("language")),ClientType.valueOf((String) body.get("type")));
            case "delete" -> service.deleteClientByName((String) body.get("name"));
            default -> "";
        };
    }
}