package org.hrsystem.dto.entryexit;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class EntryExitDTO {
    private Integer id;
    private LocalDate date;
    private LocalTime entryTime;
    private LocalTime exitTime;
    private Long lateDuration;
    private Integer employeeId;

}
