package com.belaquaa.jwt.security;

import com.belaquaa.jwt.model.Role;
import com.belaquaa.jwt.model.Roles;
import com.belaquaa.jwt.model.User;
import com.belaquaa.jwt.payload.request.LoginRequest;
import com.belaquaa.jwt.payload.request.SignupRequest;
import com.belaquaa.jwt.payload.response.JwtResponse;
import com.belaquaa.jwt.payload.response.MessageResponse;
import com.belaquaa.jwt.repository.RoleRepository;
import com.belaquaa.jwt.repository.UserRepository;
import com.belaquaa.jwt.security.jwt.JwtUtils;
import com.belaquaa.jwt.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getAge(), userDetails.getEmail(), roles);
    }

    public MessageResponse registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.username())) {
            return new MessageResponse("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.email())) {
            return new MessageResponse("Error: Email is already in use!");
        }

        User user = new User(signUpRequest.username(),
                signUpRequest.age(),
                signUpRequest.email(),
                encoder.encode(signUpRequest.password()));

        Set<Role> roles = resolveRoles(signUpRequest.role());
        user.setRoles(roles);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }

    private Set<Role> resolveRoles(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            roles.add(getRole(Roles.ROLE_USER));
        } else {
            strRoles.forEach(role -> roles.add(getRole(getRoleEnum(role))));
        }

        return roles;
    }

    private Role getRole(Roles role) {
        return roleRepository.findByRole(role)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }

    private Roles getRoleEnum(String role) {
        return switch (role) {
            case "admin" -> Roles.ROLE_ADMIN;
            case "mod" -> Roles.ROLE_MODERATOR;
            default -> Roles.ROLE_USER;
        };
    }
}