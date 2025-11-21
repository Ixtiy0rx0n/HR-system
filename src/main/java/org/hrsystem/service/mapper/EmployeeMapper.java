package org.hrsystem.service.mapper;

import lombok.RequiredArgsConstructor;
import org.hrsystem.dto.employee.EmployeeCreateDTO;
import org.hrsystem.dto.employee.EmployeeDTO;
import org.hrsystem.dto.employee.EmployeeUpdateDTO;
import org.hrsystem.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@RequiredArgsConstructor
public abstract class EmployeeMapper {

    public abstract EmployeeEntity toEntity(EmployeeCreateDTO dto);



    public abstract EmployeeDTO toDTO(EmployeeEntity entity);
    public abstract void updateEntityFromDto(EmployeeUpdateDTO dto, EmployeeEntity entity);
}
