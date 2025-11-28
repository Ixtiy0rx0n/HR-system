package org.hrsystem.dto.vacation;

import lombok.Getter;
import lombok.Setter;
import org.hrsystem.enums.Status;
import org.hrsystem.enums.VacationType;

@Setter
@Getter
public class VacationDTO {
    private Integer id;
    private Integer employeeId;
    private VacationType type;
    private Double balance;
    private Status status;
}
