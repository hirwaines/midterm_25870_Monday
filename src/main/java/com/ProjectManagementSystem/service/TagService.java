package com.ProjectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ProjectManagementSystem.modal.Tag;
import com.ProjectManagementSystem.repository.TagRepository;

@Service
public class TagService {
    
    @Autowired
    private TagRepository tagRepository;

    public String saveTag(Tag tag) {
        if (tagRepository.existsByName(tag.getName())) {
            return "Tag with this name already exists";
        }
        tagRepository.save(tag);
        return "Tag saved successfully";
    }

    public Page<Tag> getAllTags(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return tagRepository.findAll(pageable);
    }

    public boolean checkTagExists(String name) {
        return tagRepository.existsByName(name);
    }
}
