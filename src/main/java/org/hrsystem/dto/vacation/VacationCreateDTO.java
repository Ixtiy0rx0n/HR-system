package org.hrsystem.dto.vacation;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hrsystem.enums.VacationType;

@Setter
@Getter
public class VacationCreateDTO {

    @NotNull(message = "Type null bo'lmasligi kerak")
    private VacationType type;

    @NotNull(message = "Balance null bo'lmasligi kerak")
    private Double balance;

}
