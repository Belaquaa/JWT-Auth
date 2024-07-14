package com.belaquaa.jwt.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationContextHolder {

    public static UserInfo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            return new UserInfo(username);
        }
        return new UserInfo("anonymous");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfo {
        private String userId;
    }
}