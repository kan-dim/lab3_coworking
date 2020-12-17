package com.example.demo.api;

import com.example.demo.classes.MappingState;
import com.example.demo.config.MessagingConfig;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping(value = "/groups")
public class GroupController {

    @Autowired
    private RabbitTemplate template;

    @GetMapping
    public ResponseEntity<String> excursionInfo(@RequestParam(defaultValue = "00000000-0000-0000-0000-000000000000") UUID id, @RequestParam MappingState state) throws JSONException {
        String method = state == MappingState.all ? "all" : "get";
        String json = new JSONObject()
                .put("method", method)
                .put("body", new JSONObject()
                        .put("id", id)
                )
                .toString();


        Object obj = template.convertSendAndReceive(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY_GROUP, json);
        return ResponseEntity.ok(obj.toString());
    }

    @PostMapping
    public ResponseEntity<String> addNewGroup(@RequestParam int day, @RequestParam int month,
                                              @RequestParam String clientsList, @RequestParam UUID managerId) throws JSONException {

            String method = "post";
            String json = new JSONObject()
                    .put("method", method)
                    .put("body", new JSONObject()
                            .put("day", day)
                            .put("month", month)
                            .put("managerId", managerId)
                            .put("clientsList", clientsList)
                    )
                    .toString();

        Object obj = template.convertSendAndReceive(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY_GROUP, json);
        return ResponseEntity.ok(obj.toString());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteGroupByDId(@RequestParam UUID id) throws JSONException {
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