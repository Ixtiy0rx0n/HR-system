package org.hrsystem.service;

import lombok.RequiredArgsConstructor;
import org.hrsystem.repo.EntryExitRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntryExitService {
    private final EntryExitRepo repo;

}
