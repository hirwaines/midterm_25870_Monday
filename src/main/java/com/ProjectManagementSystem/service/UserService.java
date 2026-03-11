package com.ProjectManagementSystem.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ProjectManagementSystem.modal.Location;
import com.ProjectManagementSystem.modal.User;
import com.ProjectManagementSystem.modal.UserProfile;
import com.ProjectManagementSystem.repository.LocationRepository;
import com.ProjectManagementSystem.repository.UserProfileRepository;
import com.ProjectManagementSystem.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private LocationRepository locationRepository;

    public String saveUser(User user, UserProfile profile, String locationId) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "User with email already exists";
        }

        if (locationId != null) {
            Location location = locationRepository.findById(UUID.fromString(locationId)).orElse(null);
            if (location == null) {
                return "Location not found";
            }
            user.setLocation(location);
        }

        User savedUser = userRepository.save(user);
        
        if (profile != null) {
            profile.setUser(savedUser);
            userProfileRepository.save(profile);
        }

        return "User saved successfully";
    }

    public Page<User> getAllUsers(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userRepository.findAll(pageable);
    }

    public List<User> getUsersByProvince(String code, String name) {
        return userRepository.findByProvinceCodeOrName(code, name);
    }
}
