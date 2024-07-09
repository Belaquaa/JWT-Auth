package com.belaquaa.jwt.dataInit;

import com.belaquaa.jwt.model.Roles;
import com.belaquaa.jwt.model.Role;
import com.belaquaa.jwt.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final RoleRepository roleRepository;

    @Autowired
    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setRole(Roles.ROLE_ADMIN);
            roleRepository.save(adminRole);

            Role moderatorRole = new Role();
            moderatorRole.setRole(Roles.ROLE_MODERATOR);
            roleRepository.save(moderatorRole);

            Role userRole = new Role();
            userRole.setRole(Roles.ROLE_USER);
            roleRepository.save(userRole);

            Role testerRole = new Role();
            testerRole.setRole(Roles.ROLE_TESTER);
            roleRepository.save(testerRole);
        }
    }
}