package org.hrsystem.conf;

import lombok.RequiredArgsConstructor;
import org.hrsystem.entity.EmployeeEntity;
import org.hrsystem.exp.AppBadException;
import org.hrsystem.repo.EmployeeRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final EmployeeRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<EmployeeEntity> optional = repo.findByEmail(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Bad Credentials");
        }

        EmployeeEntity employee = optional.get();
        return new CustomUserDetails(employee.getId(), employee.getEmail(),
                employee.getPassword(), employee.getRole());

    }
}
