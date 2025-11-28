package org.hrsystem.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hrsystem.dto.entryexit.EntryExitCreateDTO;
import org.hrsystem.dto.entryexit.EntryExitDTO;
import org.hrsystem.dto.entryexit.EntryExitUpdate;
import org.hrsystem.entity.EmployeeEntity;
import org.hrsystem.entity.EntryExitEntity;
import org.hrsystem.exp.AppBadException;
import org.hrsystem.exp.NotFoundException;
import org.hrsystem.repo.EmployeeRepo;
import org.hrsystem.repo.EntryExitRepo;
import org.hrsystem.response.Response;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntryExitService {
    private final EntryExitRepo repo;
    private final LocalTime workStartTime = LocalTime.of(9, 0);
    private final LocalTime workEndTime = LocalTime.of(18, 0);

    private final EmployeeRepo empRepo;

    public String create(EntryExitCreateDTO dto) {

        Optional optional = empRepo.findById(dto.getEmployeeId());
        if (optional.isEmpty()) {
            throw new AppBadException("Bunday id li xodim mavjud emas");
        }
        EntryExitEntity entity = new EntryExitEntity();
        entity.setDate(dto.getDate());
        entity.setEntryTime(dto.getEntryTime());
        entity.setEmployeeId(dto.getEmployeeId());
        if (dto.getExitTime() != null) {
            entity.setExitTime(dto.getExitTime());
        }
        entity.setLateDuration(0L);
        if (dto.getEntryTime().isAfter(workStartTime)) {
            entity.setLateDuration(Duration.between(workStartTime, dto.getEntryTime()).toMinutes());
        }
        repo.save(entity);
        return dto.getEmployeeId() + " id li hodimning ma'lumotlari muvaffaqiyatli yaratildi.";
    }

    public EntryExitDTO getById(Integer id) {
        repo.findById(id).orElseThrow(() -> new AppBadException("Bunday id li ma'lumot mavjud emas"));
        EntryExitEntity entity = repo.findById(id).get();
        EntryExitDTO dto = new EntryExitDTO();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setEntryTime(entity.getEntryTime());
        dto.setExitTime(entity.getExitTime());
        dto.setLateDuration(entity.getLateDuration());
        dto.setEmployeeId(entity.getEmployeeId());
        return dto;
    }

    public EntryExitDTO updateExitTime(Integer id, EntryExitUpdate dto) {
        EntryExitEntity entity = repo.findById(id)
                .orElseThrow(() -> new AppBadException("Bunday id li ma'lumot mavjud emas"));
        if (dto.getExitTime() != null) {
            entity.setExitTime(dto.getExitTime());
        }
        if (dto.getEntryTime() != null) {
            entity.setEntryTime(dto.getEntryTime());
        }

        if (dto.getDate() != null) {
            entity.setDate(dto.getDate());
        }
        entity.setLateDuration(0L);
        if (dto.getEntryTime().isAfter(workStartTime)) {
            entity.setLateDuration(Duration.between(workStartTime, dto.getEntryTime()).toMinutes());
        }
        repo.save(entity);
        EntryExitDTO returnDto = new EntryExitDTO();
        returnDto.setId(entity.getId());
        returnDto.setDate(entity.getDate());
        returnDto.setEntryTime(entity.getEntryTime());
        returnDto.setExitTime(entity.getExitTime());
        returnDto.setLateDuration(entity.getLateDuration());
        returnDto.setEmployeeId(entity.getEmployeeId());
        return returnDto;
    }

    public Page<EntryExitDTO> getAll(@NotNull Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, "id")
        );
        Page<EntryExitEntity> entities = repo.findAll(sortedPageable);
        Page<EntryExitDTO> dtoPage = new PageImpl<>(entities.stream().map(entity -> {
            EntryExitDTO dto = new EntryExitDTO();
            dto.setId(entity.getId());
            dto.setDate(entity.getDate());
            dto.setEntryTime(entity.getEntryTime());
            dto.setExitTime(entity.getExitTime());
            dto.setLateDuration(entity.getLateDuration());
            dto.setEmployeeId(entity.getEmployeeId());
            return dto;
        }).toList(), sortedPageable, entities.getTotalElements());
        return dtoPage;
    }

    public Response countLateEntries(Integer employeeId, LocalDate date) {
        Optional optional = repo.findByEmployeeId(employeeId);
        if (optional.isEmpty()) {
            throw new NotFoundException("Bunday id li xodim mavjud emas");
        }
        EntryExitEntity entity = (EntryExitEntity) optional.get();
        if (!entity.getDate().equals(date)) {
            throw new AppBadException("Bu sanada ma'lumot mavjud emas");
        }
        return new Response("Ushbu hodim " + entity.getDate() + " sanada " + entity.getLateDuration() + " daqiqa kech qolgan", true);
    }
}
