package org.hrsystem.repo;

import org.hrsystem.entity.EmployeeEntity;
import org.hrsystem.entity.EntryExitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntryExitRepo extends JpaRepository<EntryExitEntity, Integer> {
    Optional<EntryExitEntity> findByEmployeeId(Integer employeeId);
}
