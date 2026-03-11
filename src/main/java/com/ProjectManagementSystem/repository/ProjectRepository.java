package com.ProjectManagementSystem.repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ProjectManagementSystem.modal.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    
    Boolean existsByTitle(String title);
    
    Page<Project> findAll(Pageable pageable);
}
