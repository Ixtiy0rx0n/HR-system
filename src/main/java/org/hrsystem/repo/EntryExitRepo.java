package org.hrsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryExitRepo extends JpaRepository<EntryExitRepo, Integer> {
}
