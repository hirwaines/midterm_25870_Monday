package com.ProjectManagementSystem.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ProjectManagementSystem.modal.ELocationType;
import com.ProjectManagementSystem.modal.Location;


@Repository
public interface LocationRepository  extends JpaRepository<Location, UUID> {
    
    List<Location> findByType(ELocationType type);

    List<Location> findByName(String name);

    List<Location> findByParent(Location parent);

    Location findByCodeAndName(String code, String name);

    List<Location> findByNameStartsWith(String name);

    List<Location> findByNameEndsWith(String name);

    List<Location> findByNameContains(String name);

    Boolean existsByCode(String code);

}
