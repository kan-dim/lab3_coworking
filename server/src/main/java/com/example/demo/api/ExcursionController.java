package com.example.demo.api;

import com.example.demo.classes.MappingState;
import com.example.demo.config.MessagingConfig;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping(value = "/excursions")
public class ExcursionController {
    @Autowired
    private RabbitTemplate template;

    @GetMapping
    public ResponseEntity<String> excursionInfo(@RequestParam UUID id, @RequestParam MappingState state) throws JSONException {
        String method = state == MappingState.all ? "all" : "get";
        String json = new JSONObject()
                .put("method", method)
                .put("body", new JSONObject()
                        .put("id", id)
                )
                .toString();


        Object obj = template.convertSendAndReceive(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY_MANAGER, json);
        return ResponseEntity.ok(obj.toString());
    }

    @PostMapping
    public ResponseEntity<String> addNewExcursion( @RequestParam int day, @RequestParam int month,
                                              @RequestParam int duration, @RequestParam int visitors,
                                              @RequestParam String guideName) throws JSONException {

        String method = "post";
        String json = new JSONObject()
                .put("method", method)
                .put("body", new JSONObject()
                        .put("day", day)
                        .put("month", month)
                        .put("duration", duration)
                        .put("guideName", guideName)
                        .put("visitors", visitors)
                )
                .toString();

        Object obj = template.convertSendAndReceive(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY_GROUP, json);
        return ResponseEntity.ok(obj.toString());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteExcursionById(@RequestParam UUID id) throws JSONException {
        String method = "delete";
        String json = new JSONObject()
                .put("method", method)
                .put("body", new JSONObject()
                        .put("id", id)
                )
                .toString();


        Object obj = template.convertSendAndReceive(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY_GROUP, json);
        return ResponseEntity.ok(obj.toString());
    }
}