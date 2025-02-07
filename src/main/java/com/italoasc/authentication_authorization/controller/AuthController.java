package com.italoasc.authentication_authorization.controller;

import com.italoasc.authentication_authorization.entity.User;
import com.italoasc.authentication_authorization.model.JwtResponse;
import com.italoasc.authentication_authorization.model.LoginRequest;
import com.italoasc.authentication_authorization.model.LoginResponse;
import com.italoasc.authentication_authorization.model.UserRegisterRequest;
import com.italoasc.authentication_authorization.service.MfaService;
import com.italoasc.authentication_authorization.service.UserService;
import com.italoasc.authentication_authorization.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private MfaService mfaService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateToken(request);

        User user = userService.findUserByUsername(request.getUsername());

        if (user.isMfaEnabled()) {
            mfaService.generateAndSendMfaCode(user);
            return ResponseEntity.ok(LoginResponse.builder()
                    .message("MFA code sent")
                    .token(token)
                    .build());
        }

        return ResponseEntity.ok(LoginResponse.builder()
                .message("User authenticated!")
                .token(token).build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(userService.register(userRegisterRequest));
    }
}
