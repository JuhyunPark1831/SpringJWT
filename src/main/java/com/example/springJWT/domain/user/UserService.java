package com.example.springJWT.domain.user;


import com.example.springJWT.global.exeption.CustomException;
import com.example.springJWT.global.exeption.ErrorCode;
import com.example.springJWT.global.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<?> signUp(UserDto.ReqSignUpDto reqSignUpDto) {
        User user = User.builder()
                .email(reqSignUpDto.getEmail())
                .password(reqSignUpDto.getPassword())
                .name(reqSignUpDto.getName())
                .build();

        userRepository.findByEmail(reqSignUpDto.getEmail())
                .ifPresent(m -> {
                    throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
                });

        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    public UserDto.ResLoginDto userValidation(UserDto.ReqLoginDto reqLoginDto) {
        User user = userRepository.findByEmail(reqLoginDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_REGISTERED));

        return UserDto.ResLoginDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(user.getId()))
                .build();
    }

    public UserDto.UserInfo viewUserInfo(Long userId) {
        User userInfo = userRepository.findById(userId).get();
        return UserDto.UserInfo.builder()
                .id(userInfo.getId())
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .build();
    }
}
