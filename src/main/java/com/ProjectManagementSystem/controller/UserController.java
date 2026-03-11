package com.ProjectManagementSystem.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ProjectManagementSystem.modal.User;
import com.ProjectManagementSystem.modal.UserProfile;
import com.ProjectManagementSystem.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUser(@RequestBody UserRequest request) {
        String result = userService.saveUser(request.getUser(), request.getProfile(), request.getLocationId());
        
        if (result.equals("User saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "email") String sortBy) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, sortBy));
    }

    @GetMapping(value = "/province", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsersByProvince(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name) {
        return ResponseEntity.ok(userService.getUsersByProvince(code, name));
    }

    public static class UserRequest {
        private User user;
        private UserProfile profile;
        private String locationId;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public UserProfile getProfile() {
            return profile;
        }

        public void setProfile(UserProfile profile) {
            this.profile = profile;
        }

        public String getLocationId() {
            return locationId;
        }

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }
    }
}
