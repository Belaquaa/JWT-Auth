package com.belaquaa.jwt.repository;

import com.belaquaa.jwt.model.Role;
import com.belaquaa.jwt.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(Roles role);
}