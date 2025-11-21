package org.hrsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hrsystem.enums.VacationType;

@Entity
@Table(name = "vacations")
@Setter
@Getter
public class VacationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VacationType type; // Mehnat tatili turi

    @Column(name = "balance", nullable = false)
    private Double balance; // Ta'til balansi

    @Column(name = "confirmed", nullable = false)
    private Boolean confirmed; // Tasdiqlangan yoki yo'qligi


}
