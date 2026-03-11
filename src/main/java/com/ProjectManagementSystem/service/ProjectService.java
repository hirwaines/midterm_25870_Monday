package com.ProjectManagementSystem.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ProjectManagementSystem.modal.Project;
import com.ProjectManagementSystem.modal.Tag;
import com.ProjectManagementSystem.modal.User;
import com.ProjectManagementSystem.repository.ProjectRepository;
import com.ProjectManagementSystem.repository.TagRepository;
import com.ProjectManagementSystem.repository.UserRepository;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    public String saveProject(Project project, String userId, List<String> tagNames) {
        if (projectRepository.existsByTitle(project.getTitle())) {
            return "Project with this title already exists";
        }

        User user = userRepository.findById(UUID.fromString(userId)).orElse(null);
        if (user == null) {
            return "User not found";
        }
        project.setUser(user);

        if (tagNames != null && !tagNames.isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (String tagName : tagNames) {
                Tag tag = tagRepository.findByName(tagName).orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setName(tagName);
                    return tagRepository.save(newTag);
                });
                tags.add(tag);
            }
            project.setTags(tags);
        }

        projectRepository.save(project);
        return "Project saved successfully";
    }

    public Page<Project> getAllProjects(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return projectRepository.findAll(pageable);
    }

    public boolean checkProjectExists(String title) {
        return projectRepository.existsByTitle(title);
    }
}
