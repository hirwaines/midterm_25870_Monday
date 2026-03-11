package com.ProjectManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ProjectManagementSystem.modal.Location;
import com.ProjectManagementSystem.service.LocationService;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    

    @Autowired
    private LocationService locationService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveLocation(@RequestBody Location location, @RequestParam(required = false) String parentId){
        String registerLocation = locationService.saveLocation(location, parentId);

        if(registerLocation.equals("Location saved successfully")) {
            return new ResponseEntity<>(registerLocation, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(registerLocation, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLocationByCodeAndName(@RequestParam String code, @RequestParam String name){
        Location location = locationService.getLocationByCodeAndName(code , name);
        
        if(location != null) {
            return new ResponseEntity<>(location, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Location not found", HttpStatus.NOT_FOUND);
        }
    }
}
