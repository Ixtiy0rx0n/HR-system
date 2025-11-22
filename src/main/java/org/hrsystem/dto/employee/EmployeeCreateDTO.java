package org.hrsystem.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeCreateDTO {
    @NotBlank(message = "Ism bo'sh bo'lmasligi kerak")
    private String firstName;

    @NotBlank(message = "Familiya bo'sh bo'lmasligi kerak")
    private String lastName;

    @NotBlank(message = "Email bo'sh bo'lmasligi kerak")
    private String email;

    @NotBlank(message = "Parol bo'sh bo'lmasligi kerak")
    private String password;

    @NotBlank(message = "Lavozim bo'sh bo'lmasligi kerak")
    private String position;
    
    @NotBlank(message = "Oylik bo'sh bo'lmasligi kerak")
    private String salary;
}
