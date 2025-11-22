package org.hrsystem.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hrsystem.dto.vacation.VacationCreateDTO;
import org.hrsystem.dto.vacation.VacationDTO;
import org.hrsystem.entity.VacationEntity;
import org.hrsystem.exp.AppBadException;
import org.hrsystem.exp.NotFoundException;
import org.hrsystem.repo.VacationRepo;
import org.hrsystem.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacationService {
    private final VacationRepo repo;

    public Response<Boolean> create(VacationCreateDTO dto) {
        VacationEntity entity = new VacationEntity();
        entity.setEmployeeId(null); // TODO: Employee ID ni jwt dan olib set qilish kerak
        entity.setType(dto.getType());
        entity.setBalance(dto.getBalance());
        entity.setConfirmed(false);
        repo.save(entity);
        return new Response<>("Mehnat ta'tili uchun ariza yaratildi.", true);
    }

    public Double getBalanceById(Integer employeeId) {
        //TODO: Employee ID ni jwt dan olib tekshirish kerak
        VacationEntity entity = repo.findByEmployeeId(employeeId);
        if (entity != null) {
            return entity.getBalance();
        }
        throw new AppBadException("Balansni olishda xatolik yuz berdi.");
    }

    public String confirmedVacation(Integer employeeId) {
        VacationEntity entity = repo.findByEmployeeId(employeeId);
        if (entity == null) {
            throw new NotFoundException("Ushbu hodimning Mehnat ta'tili topilmadi.");
        }

        if (entity.getConfirmed().equals(true)) {
            return "Mehnat ta'tili allaqachon tasdiqlangan.";
        }
        entity.setConfirmed(true);
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
            dto.setConfirmed(entity.getConfirmed());
            return dto;
        });
    }
}
