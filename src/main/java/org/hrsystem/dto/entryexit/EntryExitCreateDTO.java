package org.hrsystem.dto.entryexit;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class EntryExitCreateDTO {
    @NotBlank(message = "Hodim id sini null bo'lmasligi kerak")
    private Integer employeeId;

    @NotBlank(message = "Sana null bo'lmasligi kerak")
    private LocalDate date;

    @NotBlank(message = "Kirish vaqti null bo'lmasligi kerak")
    private LocalTime entryTime;

    private LocalTime exitTime;
}
