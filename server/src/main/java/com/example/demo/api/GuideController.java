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
@RequestMapping(value = "/guides")
public class GuideController {

    @Autowired
    private RabbitTemplate template;

    @GetMapping
    public ResponseEntity<String> guiderInfo(@RequestParam String name, @RequestParam MappingState state) throws JSONException {

        
        String method = state == MappingState.byName ? "get" : state.toString();
        String json = new JSONObject()
                .put("method", method)
                .put("body", new JSONObject()
                        .put("name", name)
                )
                .toString();


        Object obj = template.convertSendAndReceive(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY_GUIDE, json);
        return ResponseEntity.ok(obj.toString());
    }



    @PostMapping
    public ResponseEntity<String> addNewGuide(@RequestParam String name, @RequestParam boolean knowEnglish) throws JSONException {

        String method = "post";
        String json = new JSONObject()
                .put("method", method)
                .put("body", new JSONObject()
                        .put("name", name)
                        .put("knowEnglish", knowEnglish)
                )
                .toString();

        System.out.println(json);


        Object obj = template.convertSendAndReceive(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY_GUIDE, json);
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


        Object obj = template.convertSendAndReceive(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY_GUIDE, json);
        return ResponseEntity.ok(obj.toString());
    }

    @PutMapping
    public ResponseEntity<String> toggleFreeGuideState(@RequestParam UUID id) throws JSONException {
        String method = "put";
        String json = new JSONObject()
                .put("method", method)
                .put("body", new JSONObject()
                        .put("id", id)
                )
                .toString();


        Object obj = template.convertSendAndReceive(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY_GUIDE, json);
        return ResponseEntity.ok(obj.toString());
    }


}