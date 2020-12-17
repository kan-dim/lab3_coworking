package com.example.demo.api;

import com.example.demo.classes.MappingState;
import com.example.demo.config.MessagingConfig;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/managers")
public class ManagerController {

    @Autowired
    private RabbitTemplate template;

    @GetMapping
    public ResponseEntity<String> managersInfo(@RequestParam String name, @RequestParam MappingState state) throws JSONException {
        String method = state == MappingState.all ? "all" : "get";
        String json = new JSONObject()
                .put("method", method)
                .put("body", new JSONObject()
                        .put("name", name)
                )
                .toString();


        Object obj = template.convertSendAndReceive(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY_MANAGER, json);
        return ResponseEntity.ok(obj.toString());
    }

    @PostMapping
    public ResponseEntity<String> addManager(@RequestParam String name, @RequestParam boolean knowEnglish) throws JSONException {
        String method = "post";
        String json = new JSONObject()
                .put("method", method)
                .put("body", new JSONObject()
                        .put("name", name)
                        .put("knowEnglish", knowEnglish)
                )
                .toString();


        Object obj = template.convertSendAndReceive(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY_MANAGER, json);
        return ResponseEntity.ok(obj.toString());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteManagerByName(@RequestParam String name) throws JSONException {

        String method = "delete";
        String json = new JSONObject()
                .put("method", method)
                .put("body", new JSONObject()
                        .put("name", name)
                )
                .toString();


        Object obj = template.convertSendAndReceive(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY_MANAGER, json);
        return ResponseEntity.ok(obj.toString());
    }
}