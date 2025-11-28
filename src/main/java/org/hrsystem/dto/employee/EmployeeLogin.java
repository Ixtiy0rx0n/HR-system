package org.hrsystem.dto.employee;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeLogin {
    @NotBlank(message = "Email bo'sh bo'lmasligi kerak")
    private String email;
    @NotBlank(message = "Parol bo'sh bo'lmasligi kerak")
    private String password;
}
