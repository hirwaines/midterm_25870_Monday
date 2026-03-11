package com.ProjectManagementSystem.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ProjectManagementSystem.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.location.type = 'PROVINCE' AND (u.location.code = ?1 OR u.location.name = ?2)")
    List<User> findByProvinceCodeOrName(String code, String name);

    Page<User> findAll(Pageable pageable);
}
