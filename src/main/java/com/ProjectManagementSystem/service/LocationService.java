package com.ProjectManagementSystem.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ProjectManagementSystem.modal.Location;
import com.ProjectManagementSystem.repository.LocationRepository;

@Service
public class LocationService {
    

    @Autowired
    private LocationRepository locationRepository;

    // public String saveParent(Location location) {
    //    Boolean checkExists = locationRepository.existsByCode(location.getCode());
    //      if(checkExists) {
    //       return "Location with code " + location.getCode() + " already exists";
    //      }else{
    //         locationRepository.save(location);
    //         return "Location saved successfully";
    //      }

    // }

    public String saveLocation(Location location, String parentId) {
        if(parentId != null) {
            Location parent = locationRepository.findById(UUID.fromString(parentId)).orElse(null);
            if(parent == null) {
                return "Parent location not found";
            }
            location.setParent(parent);
        }
        // locationRepository.save(location);
        // return "Location saved successfully";

        Boolean checkExists = locationRepository.existsByCode(location.getCode());
        if(checkExists) {
        return "Location with code " + location.getCode() + " already exists";
        }else{
            locationRepository.save(location);
            return "Location saved successfully";
        }

    }

    public Location getLocationByCodeAndName(String code, String name) {
        return locationRepository.findByCodeAndName(code, name);
    }
}
