package com.ProjectManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ProjectManagementSystem.modal.Tag;
import com.ProjectManagementSystem.service.TagService;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    
    @Autowired
    private TagService tagService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveTag(@RequestBody Tag tag) {
        String result = tagService.saveTag(tag);
        
        if (result.equals("Tag saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Tag>> getAllTags(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        return ResponseEntity.ok(tagService.getAllTags(page, size, sortBy));
    }

    @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkTagExists(@RequestParam String name) {
        boolean exists = tagService.checkTagExists(name);
        return ResponseEntity.ok(new CheckResponse(exists, 
            exists ? "Tag already exists" : "Tag available"));
    }

    public static class CheckResponse {
        private boolean exists;
        private String message;

        public CheckResponse(boolean exists, String message) {
            this.exists = exists;
            this.message = message;
        }

        public boolean isExists() {
            return exists;
        }

        public String getMessage() {
            return message;
        }
    }
}
