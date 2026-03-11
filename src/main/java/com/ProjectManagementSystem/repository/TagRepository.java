package com.ProjectManagementSystem.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ProjectManagementSystem.modal.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    
    Optional<Tag> findByName(String name);
    
    Boolean existsByName(String name);
}
