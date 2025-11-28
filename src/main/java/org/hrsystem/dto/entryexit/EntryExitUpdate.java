package org.hrsystem.dto.entryexit;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class EntryExitUpdate {
    private LocalDate date;
    private LocalTime entryTime;
    private LocalTime exitTime;
}
