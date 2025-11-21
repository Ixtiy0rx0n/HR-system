package org.hrsystem.dto.employee;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeUpdateDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String position;
    private Double salary;
}
