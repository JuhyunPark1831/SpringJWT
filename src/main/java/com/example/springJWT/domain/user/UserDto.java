package com.example.springJWT.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Bean;

public class UserDto {

    @Getter
    @AllArgsConstructor
    public static class ReqSignUpDto{
        private String name;
        private String email;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class ReqLoginDto{
        private String email;
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ResLoginDto{
        String accessToken;
        String refreshToken;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UserInfo{
        Long id;
        String email;
        String name;
    }
}
