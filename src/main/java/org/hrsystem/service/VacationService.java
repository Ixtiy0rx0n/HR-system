package org.hrsystem.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hrsystem.dto.vacation.VacationCreateDTO;
import org.hrsystem.dto.vacation.VacationDTO;
import org.hrsystem.entity.EmployeeEntity;
import org.hrsystem.entity.VacationEntity;
import org.hrsystem.enums.Status;
import org.hrsystem.exp.AppBadException;
import org.hrsystem.exp.NotFoundException;
import org.hrsystem.repo.EmployeeRepo;
import org.hrsystem.repo.VacationRepo;
import org.hrsystem.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacationService {
    private final VacationRepo repo;
    private final EmployeeRepo employeeRepo;

    public Response<Boolean> create(VacationCreateDTO dto, String employeeEmail) {

        VacationEntity entity = new VacationEntity();
        entity.setEmployeeId(employeeRepo.findByEmail1(employeeEmail).getId());

        entity.setType(dto.getType());
        entity.setBalance(dto.getBalance());
        entity.setStatus(Status.PENDING);
        repo.save(entity);
        return new Response<>("Mehnat ta'tili uchun ariza yaratildi.", true);
    }

    public Double getBalanceByEmail(String employeeEmail) {
        EmployeeEntity employee = employeeRepo.findByEmail1(employeeEmail);
        VacationEntity entity = repo.findByEmployeeId(employee.getId());
        if (entity != null) {
            return entity.getBalance();
        }
        throw new AppBadException("Balansni olishda xatolik yuz berdi.");
    }

    public String confirmedVacation(Integer employeeId, Status status) {
        VacationEntity entity = repo.findByEmployeeId(employeeId);
        if (entity == null) {
            throw new NotFoundException("Ushbu hodimning Mehnat ta'tili topilmadi.");
        }

        if (Status.CONFIRMED.equals(entity.getStatus())) {
            return "Mehnat ta'tili allaqachon tasdiqlangan.";
        }
        entity.setStatus(status);
        repo.save(entity);
        return "Mehnat ta'tili tasdiqlandi.";
    }

    public Page<VacationDTO> getAll(@NotNull Pageable pageable) {
        return repo.findAll(pageable).map(entity -> {
            VacationDTO dto = new VacationDTO();
            dto.setId(entity.getId());
            dto.setEmployeeId(entity.getEmployeeId());
            dto.setType(entity.getType());
            dto.setBalance(entity.getBalance());
            dto.setStatus(entity.getStatus());
            return dto;
        });
    }

    public VacationDTO getById(Integer id) {
        VacationEntity entity = repo.findById(id).orElseThrow(
                () -> new NotFoundException("id " + id + " li mehnat ta'tili topilmadi")
        );
        VacationDTO dto = new VacationDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setType(entity.getType());
        dto.setBalance(entity.getBalance());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
