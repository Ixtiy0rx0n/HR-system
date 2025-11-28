package org.hrsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hrsystem.dto.vacation.VacationCreateDTO;
import org.hrsystem.dto.vacation.VacationDTO;
import org.hrsystem.enums.Status;
import org.hrsystem.response.Response;
import org.hrsystem.service.VacationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Vacation APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/vacation")
public class VacationController {

    private final VacationService service;

    @PreAuthorize("hasAnyAuthority('MANAGER','EMPLOYEE')")
    @Operation(summary = "Create vacation request")
    @PostMapping("/")
    public Response<Boolean> create(@NotNull @RequestBody VacationCreateDTO dto,
                                    Authentication authentication) {
        log.info("Employee created {}", dto);
        return service.create(dto, authentication.getName());
    }

    @PreAuthorize("hasAnyAuthority('MANAGER','EMPLOYEE')")
    @Operation(summary = "Get vacation balance")
    @GetMapping("/balance")
    public Double getBalance(Authentication authentication) {
        return service.getBalanceByEmail(authentication.getName());
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Operation(summary = "Approve or reject vacation request")
    @GetMapping("/{employeeId}")
    public String approveOrReject(@PathVariable("employeeId") Integer employeeId,
                                  @RequestParam Status status) {
        return service.confirmedVacation(employeeId, status);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Operation(summary = "Get all vacation with pagination")
    @GetMapping("/all")
    public Page<VacationDTO> getAll(
            @PageableDefault @Schema(hidden = true) Pageable pageable) {
        return service.getAll(pageable);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER','EMPLOYEE')")
    @Operation(summary = "Get vacation by id")
    @GetMapping("/get/{id}")
    public VacationDTO getById(@PathVariable("id") Integer id) {
        return service.getById(id);
    }
}
