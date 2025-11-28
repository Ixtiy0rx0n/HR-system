package org.hrsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hrsystem.enums.Role;

@Entity
@Table(name = "employees")
@Setter
@Getter
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "position")
    private String position; // lavozim

    @Column(name = "salary")
    private Double salary; // oylik

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
}
