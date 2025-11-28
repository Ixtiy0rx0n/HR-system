package org.hrsystem.controller;

import io.jsonwebtoken.security.PublicJwkBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hrsystem.dto.employee.EmployeeCreateDTO;
import org.hrsystem.dto.employee.EmployeeDTO;
import org.hrsystem.dto.employee.EmployeeLogin;
import org.hrsystem.dto.employee.EmployeeUpdateDTO;
import org.hrsystem.dto.vacation.VacationDTO;
import org.hrsystem.response.Response;
import org.hrsystem.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Employee APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService service;

    @Operation(summary = "Login api")
    @PostMapping("/login")
    public EmployeeDTO login(@NotNull @RequestBody EmployeeLogin dto) {
        log.info("Employee login {}", dto);
        return service.login(dto);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Operation(summary = "Create employee")
    @PostMapping("/")
    public Response<Boolean> create(@NotNull @RequestBody EmployeeCreateDTO dto) {
        log.info("Employee created {}", dto);
        return service.create(dto);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @Operation(summary = "Update employee by id")
    @PutMapping("/{id}")
    public Response<EmployeeDTO> updateById(@NotNull @RequestBody EmployeeUpdateDTO dto,
                                            @NotNull @PathVariable("id") Integer id) {
        log.info("Employee updated {}", dto);
        return service.updateById(dto, id);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Operation(summary = "Delete employee by id")
    @DeleteMapping("/{id}")
    public Response<Boolean> deleteById(@NotNull @PathVariable("id") Integer id) {
        log.warn("Employee deleted {}", id);
        return service.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'EMPLOYEE')")
    @Operation(summary = "Get employee by id")
    @GetMapping("/{id}")
    public Response<EmployeeDTO> getById(@NotNull @PathVariable("id") Integer id) {
        return service.getById(id);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Operation(summary = "Get all employees with pagination")
    @GetMapping("/")
    public Page<EmployeeDTO> getAll(@PageableDefault @Schema(hidden = true) Pageable pageable) {
        return service.getAll(pageable);
    }

    @Operation(summary = "Init admin employee")
    @GetMapping("/init")
    public EmployeeDTO initAdmin() {
        return service.initAdmin();
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Operation(summary = "Get employee by position name")
    @GetMapping("/position")
    public Page<EmployeeDTO> getByPositionName(
            @PageableDefault @Schema(hidden = true) Pageable pageable,
            @RequestParam String positionName) {
        return service.getByPosition(pageable, positionName);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @Operation(summary = "Search employees by name or surname")
    @GetMapping("/search")
    public Page<EmployeeDTO> searchByNameOrSurname(
            @PageableDefault @Schema(hidden = true) Pageable pageable,
            @RequestParam String search) {
        return service.searchByName(search, pageable);
    }
}
