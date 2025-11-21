package org.hrsystem.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hrsystem.dto.employee.EmployeeCreateDTO;
import org.hrsystem.dto.employee.EmployeeDTO;
import org.hrsystem.dto.employee.EmployeeUpdateDTO;
import org.hrsystem.entity.EmployeeEntity;
import org.hrsystem.enums.Role;
import org.hrsystem.exp.NotFoundException;
import org.hrsystem.repo.EmployeeRepo;
import org.hrsystem.response.Response;
import org.hrsystem.service.mapper.EmployeeMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;


@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo repo;
    private final EmployeeMapper mapper;

    public Response<Boolean> create(EmployeeCreateDTO dto) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPosition(dto.getPosition());
        entity.setSalary(Double.valueOf(dto.getSalary()));
        entity.setRole(Role.EMPLOYEE);
        repo.save(entity);
        return new Response<>("Hodim muvaffaqiyatli yaratildi.", true);
    }

    public Response<EmployeeDTO> updateById(EmployeeUpdateDTO dto) {
        if (dto.getId() == null) {
            throw new NotFoundException("Id kiritilishi shart.");
        }

        EmployeeEntity entity = repo.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Hodim topilmadi."));

        if (dto.getFirstName() == null && dto.getLastName() == null &&
            dto.getPosition() == null && dto.getSalary() == null) {
            throw new NotFoundException("Yangilash uchun hech narsa topilmadi.");
        }
        mapper.updateEntityFromDto(dto, entity);
        repo.save(entity);

        EmployeeDTO returnDTO = mapper.toDTO(entity);
        return new Response<>("Hodim muvaffaqiyatli yangilandi.", returnDTO);
    }

    public Response<Boolean> deleteById(Integer id) {
        //TODO : Hodimni faqat MANAGER o'chira olishi kerak.
        repo.findById(id).orElseThrow(() -> new NotFoundException("Hodim topilmadi."));
        repo.deleteById(id);
        return new Response<>("Hodim muvaffaqiyatli o'chirildi.", true);
        // TODO : hodim ochirilganda unga tegishli boshqa table dagi ma'lumotlarni ham o'chirish kerak.
        // EntryExit, VacationEntity table lardagi ma'lumotlarni ham o'chirish kerak.
    }

    public Response<EmployeeDTO> getById(Integer id) {
        //TODO: Hodim ma'lumotlarini o'zi va MANAGER ko'ra olishi kerak. JWT orqali tekshirish kerak.
        EmployeeEntity entity = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Hodim topilmadi."));
        EmployeeDTO dto = mapper.toDTO(entity);
        return new Response<>("Hodim topildi.", dto);
    }

    public Page<EmployeeDTO> getAll(@NotNull Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, "id")
        );
        return repo.findAll(sortedPageable).map(mapper::toDTO);
    }
}
