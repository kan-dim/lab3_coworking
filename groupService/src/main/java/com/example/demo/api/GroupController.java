package com.example.demo.api;

import com.example.demo.classes.service.GroupService;
import com.example.demo.classes.MappingState;
import com.example.demo.config.MessagingConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;


@Component
public class GroupController {

    private final GroupService service;

    @Autowired
    public GroupController(GroupService service) {
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
            case "all" -> service.groupsInfo();
            case "get" -> service.getGroupById(UUID.fromString((String) body.get("id")));
            case "post" -> service.addNewGroup(Integer.parseInt(body.get("day").toString()), Integer.parseInt(body.get("month").toString()),
                    UUID.fromString((String) body.get("managerId")),(String) body.get("clientsList"));
            case "delete" -> service.deleteGroupById(UUID.fromString((String) body.get("id")));
            default -> "";
        };
    }
}