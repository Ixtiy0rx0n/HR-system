package org.hrsystem.repo;

import org.hrsystem.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Integer> {
    @Query("SELECT e FROM EmployeeEntity e WHERE e.email = :email")
    EmployeeEntity findByEmail1(String email);
    Optional<EmployeeEntity> findByEmail(String email);

    Optional<EmployeeEntity> findByPosition(String position);

    Page<EmployeeEntity> findByPositionContainingIgnoreCase(String position, Pageable sortedPageable);

    Page<EmployeeEntity> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName,
            String lastName,
            Pageable pageable
    );

}
