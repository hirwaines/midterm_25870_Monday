package com.ProjectManagementSystem.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ProjectManagementSystem.modal.Project;
import com.ProjectManagementSystem.service.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveProject(@RequestBody ProjectRequest request) {
        String result = projectService.saveProject(request.getProject(), request.getUserId(), request.getTags());
        
        if (result.equals("Project saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Project>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        return ResponseEntity.ok(projectService.getAllProjects(page, size, sortBy));
    }

    @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkProjectExists(@RequestParam String title) {
        boolean exists = projectService.checkProjectExists(title);
        return ResponseEntity.ok(new CheckResponse(exists, 
            exists ? "Project title already used" : "Project title available"));
    }

    public static class ProjectRequest {
        private Project project;
        private String userId;
        private List<String> tags;

        public Project getProject() {
            return project;
        }

        public void setProject(Project project) {
            this.project = project;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
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
