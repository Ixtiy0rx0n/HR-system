package org.hrsystem.dto.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hrsystem.enums.Role;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String position;
    private String salary;
    private Role role;
}
