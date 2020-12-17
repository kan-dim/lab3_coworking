package com.example.demo.api;

import com.example.demo.classes.service.ManagerService;
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
public class ManagerController {

    private final ManagerService service;

    @Autowired
    public ManagerController(ManagerService service) {
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
            case "all" -> service.managersInfo();
            case "get" -> service.getManagerByName((String) body.get("name"));
            case "post" -> service.addNewManager((String) body.get("name"),Boolean.parseBoolean((String) body.get("knowEnglish")));
            case "delete" -> service.deleteManagerByName((String) body.get("name"));
            default -> "";
        };
    }
}

