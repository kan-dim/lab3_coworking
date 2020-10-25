package com.example.demo.api;

import com.example.demo.classes.service.GroupService;
import com.example.demo.classes.MappingState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping(value = "/groups")
public class GroupController {

    private final GroupService service;

    @Autowired
    public GroupController(GroupService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<String> excursionInfo(@RequestParam UUID id, @RequestParam MappingState state) {
        if (state == MappingState.all) return ResponseEntity.ok(service.groupsInfo());
        return ResponseEntity.ok(service.getGroupById(id));
    }

    @PostMapping
    public ResponseEntity<String> addNewGroup(@RequestParam int day, @RequestParam int month,
                                              @RequestParam String clientsList, @RequestParam UUID managerId,
                                              @RequestParam UUID excursionId) {
        return ResponseEntity.ok(service.addNewGroup(day, month, managerId, excursionId, clientsList));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteGroupByDId(@RequestParam UUID id) {
        return ResponseEntity.ok(service.deleteGroupById(id));
    }
}