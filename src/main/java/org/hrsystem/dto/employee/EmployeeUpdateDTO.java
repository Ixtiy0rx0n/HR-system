package org.hrsystem.dto.employee;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeUpdateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String position;
    private Double salary;
}
