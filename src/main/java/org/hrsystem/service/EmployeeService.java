package org.hrsystem.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hrsystem.dto.employee.EmployeeCreateDTO;
import org.hrsystem.dto.employee.EmployeeDTO;
import org.hrsystem.dto.employee.EmployeeLogin;
import org.hrsystem.dto.employee.EmployeeUpdateDTO;
import org.hrsystem.dto.vacation.VacationDTO;
import org.hrsystem.entity.EmployeeEntity;
import org.hrsystem.enums.Role;
import org.hrsystem.exp.AppBadException;
import org.hrsystem.exp.NotFoundException;
import org.hrsystem.repo.EmployeeRepo;
import org.hrsystem.response.Response;
import org.hrsystem.service.mapper.EmployeeMapper;
import org.hrsystem.util.JwtUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo repo;
    private final EmployeeMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public EmployeeDTO login(EmployeeLogin dto) {
        Optional<EmployeeEntity> optional = repo.findByEmail(dto.getEmail());
        if (optional.isEmpty()) {
            throw new AppBadException("Noto'g'ri email yoki parol.");
        }

        EmployeeEntity entity = optional.get();
        if (!passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
            throw new AppBadException("Noto'g'ri email yoki parol.");
        }

        EmployeeDTO returnDto = mapper.toDTO(entity);
        returnDto.setJwt(JwtUtil.encode(returnDto.getEmail(), entity.getRole()));
        return returnDto;
    }

    public Response<Boolean> create(EmployeeCreateDTO dto) {
        Optional optional = repo.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new AppBadException("Bu email allaqachon mavjud.");
        }
        EmployeeEntity entity = new EmployeeEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setPosition(dto.getPosition());
        entity.setSalary(Double.valueOf(dto.getSalary()));
        entity.setRole(Role.EMPLOYEE);
        repo.save(entity);
        return new Response<>("Hodim muvaffaqiyatli yaratildi.", true);
    }

    public Response<EmployeeDTO> updateById(EmployeeUpdateDTO dto, Integer id) {
        if (id == null) {
            throw new NotFoundException("Id kiritilishi shart.");
        }

        EmployeeEntity entity = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Hodim topilmadi."));

        if (dto.getFirstName() == null && dto.getLastName() == null &&
            dto.getPosition() == null && dto.getSalary() == null) {
            throw new NotFoundException("Yangilash uchun hech narsa topilmadi.");
        }
        mapper.updateEntityFromDto(dto, entity);
        repo.save(entity);

        EmployeeDTO returnDTO = mapper.toDTO(entity);
        returnDTO.setId(id);
        return new Response<>("Hodim muvaffaqiyatli yangilandi.", returnDTO);
    }

    public Response<Boolean> deleteById(Integer id) {

        repo.findById(id).orElseThrow(() -> new NotFoundException("Hodim topilmadi."));
        repo.deleteById(id);
        return new Response<>("Hodim muvaffaqiyatli o'chirildi.", true);
        // TODO : hodim ochirilganda unga tegishli boshqa table dagi ma'lumotlarni ham o'chirish kerak.
        // EntryExit, VacationEntity table lardagi ma'lumotlarni ham o'chirish kerak.
    }

    public Response<EmployeeDTO> getById(Integer id) {
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

    public EmployeeDTO initAdmin() {
        Optional optional = repo.findByEmail("ixti@gmail.com");
        if (optional.isPresent()) {
            throw new AppBadException("Admin allaqachon mavjud.");
        }

        EmployeeEntity entity = new EmployeeEntity();
        entity.setFirstName("Ixtiyorxon");
        entity.setLastName("Xabibulloyev");
        entity.setEmail("ixti@gmail.com");
        entity.setPassword(passwordEncoder.encode("123"));
        entity.setPosition("ADMIN");
        entity.setSalary(10000.0);
        entity.setRole(Role.MANAGER);
        repo.save(entity);

        EmployeeDTO dto = mapper.toDTO(entity);
        dto.setJwt(JwtUtil.encode(dto.getEmail(), Role.MANAGER));
        return dto;
    }

    public Page<EmployeeDTO> getByPosition(@NotNull Pageable pageable,
                                           @NotNull String position) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, "id")
        );
        return repo.findByPositionContainingIgnoreCase(position, sortedPageable)
                .map(mapper::toDTO);
    }

    public Page<EmployeeDTO> searchByName(String search,
                                          @NotNull Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, "id")
        );
        Page<EmployeeEntity> entities = repo.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                search, search, sortedPageable);
        return entities.map(mapper::toDTO);
    }

}
