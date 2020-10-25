package com.example.demo.api;

import com.example.demo.classes.service.GuideService;
import com.example.demo.classes.MappingState;
import com.example.demo.classes.people.Guide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/guides")
public class GuideController {

    private final GuideService service;

    @Autowired
    public GuideController(GuideService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<String> guiderInfo(@RequestParam String name, @RequestParam MappingState state) {
        if (state == MappingState.all) return ResponseEntity.ok(service.guideInfo());
        if (state == MappingState.free) return ResponseEntity.ok(service.getFreeGuides());
        if (state == MappingState.byName) return ResponseEntity.ok(service.getGuideIdByName(name));
        return ResponseEntity.ok(service.getGuideByName(name));
    }



    @PostMapping
    public ResponseEntity<Guide> addNewGuide(@RequestParam String name, @RequestParam boolean knowEnglish) {
        return ResponseEntity.ok(service.addNewGuide(name, knowEnglish));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteManagerByName(@RequestParam String name) {
        return ResponseEntity.ok(service.deleteGuideByName(name));
    }

    @PutMapping
    public ResponseEntity<Boolean> toggleFreeGuideState(@RequestParam UUID id) {
        return ResponseEntity.ok(service.toggleGuideFreeState(id));
    }


}