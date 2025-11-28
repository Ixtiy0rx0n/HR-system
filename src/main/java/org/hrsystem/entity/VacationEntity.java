package org.hrsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hrsystem.enums.Status;
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

    @ManyToOne
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private EmployeeEntity employee;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VacationType type; // Mehnat tatili turi

    @Column(name = "balance", nullable = false)
    private Double balance; // Ta'til balansi

    @Column(name = "confirmed", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status; // Tasdiqlangan yoki yo'qligi


}
