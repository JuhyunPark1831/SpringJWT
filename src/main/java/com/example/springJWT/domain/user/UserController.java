package com.example.springJWT.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/user/join")
    public ResponseEntity<?> signUp(@RequestBody UserDto.ReqSignUpDto reqSignUpDto) {
        return ResponseEntity.ok(userService.signUp(reqSignUpDto));
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> signIn(@RequestBody UserDto.ReqLoginDto reqLoginDto) {
        return ResponseEntity.ok(userService.userValidation(reqLoginDto));
    }

    @GetMapping("/user/info")
    public ResponseEntity<?> userInfo(@AuthenticationPrincipal Long userId) {
        UserDto.UserInfo userInfo = userService.viewUserInfo(userId);
        return ResponseEntity.ok(userInfo);
    }
}
