package org.hrsystem.dto;

import lombok.Getter;
import lombok.Setter;
import org.hrsystem.enums.Role;

@Setter
@Getter
public class JwtDTO {
    private Integer id;
    private String email;
    private Role role;

    public JwtDTO(Integer id) {
        this.id = id;
    }

    public JwtDTO(Integer id, Role role) {
        this.id = id;
        this.role = role;
    }

    public JwtDTO(String email, Role role) {
        this.email = email;
        this.role = role;
    }


    public JwtDTO(Integer id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
