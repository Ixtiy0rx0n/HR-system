package org.hrsystem.repo;

import org.hrsystem.entity.VacationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationRepo extends JpaRepository<VacationEntity, Integer> {
    VacationEntity findByEmployeeId(Integer employeeId);
}
