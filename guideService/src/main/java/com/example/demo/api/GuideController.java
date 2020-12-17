package com.example.demo.api;

import com.example.demo.classes.service.GuideService;
import com.example.demo.config.MessagingConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class GuideController {

    private final GuideService service;

    @Autowired
    public GuideController(GuideService service) {
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
            case "all" -> service.guideInfo();
            case "free" -> service.getFreeGuides();
            case "get" -> service.getGuideIdByName((String) body.get("name"));
            case "post" -> service.addNewGuide((String) body.get("name"), "true" == body.get("knowEnglish").toString());
            case "delete" -> service.deleteGuideByName((String) body.get("name"));
            case "put" -> service.toggleGuideFreeState(UUID.fromString((String) body.get("id")));
            default -> "";
        };
    }
}