package org.hrsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hrsystem.dto.entryexit.EntryExitCreateDTO;
import org.hrsystem.dto.entryexit.EntryExitDTO;
import org.hrsystem.dto.entryexit.EntryExitUpdate;
import org.hrsystem.response.Response;
import org.hrsystem.service.EntryExitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@Tag(name = "Entry exit APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/entry-exit")
public class EntryExitController {
    private final EntryExitService service;

    @PreAuthorize("hasAuthority('MANAGER')")
    @Operation(summary = "Create entry exit record")
    @PostMapping("/")
    public String create(@NotNull @RequestBody EntryExitCreateDTO dto) {
        log.info("Create entry exit record");
        return service.create(dto);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Operation(summary = "Get by id")
    @GetMapping("/{id}")
    public EntryExitDTO getById(@PathVariable("id") Integer id) { //permitAll
        return service.getById(id);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Operation(summary = "Update Entry exit record")
    @PutMapping("/{id}")
    public EntryExitDTO update(@PathVariable("id") Integer id,
                               @RequestBody @NotNull EntryExitUpdate dto) {
        log.info("Update entry exit record id: {}", id);
        return service.updateExitTime(id, dto);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Operation(summary = "Get all entry exit records")
    @GetMapping("/all")
    public Page<EntryExitDTO> getAll(@NotNull Pageable pageable) {
        return service.getAll(pageable);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Operation(summary = "Hodimni ushbu sanada nechi daqiqa kech qolganligini aniqlash")
    @GetMapping("/late")
    public Response getLateMinutes(@RequestParam("employeeId") Integer employeeId,
                                   @RequestParam("date") LocalDate date) {
        return service.countLateEntries(employeeId, date);

    }
}
