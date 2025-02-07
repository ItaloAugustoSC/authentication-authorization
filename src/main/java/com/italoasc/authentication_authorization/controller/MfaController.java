package com.italoasc.authentication_authorization.controller;

import com.italoasc.authentication_authorization.entity.User;
import com.italoasc.authentication_authorization.mapper.MfaMapper;
import com.italoasc.authentication_authorization.model.JwtResponse;
import com.italoasc.authentication_authorization.model.MfaRequest;
import com.italoasc.authentication_authorization.repository.UserRepository;
import com.italoasc.authentication_authorization.service.MfaService;
import com.italoasc.authentication_authorization.service.UserService;
import com.italoasc.authentication_authorization.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.italoasc.authentication_authorization.mapper.MfaMapper.mfaMapper;

@RestController
@RequestMapping("/mfa")
public class MfaController {
    @Autowired
    private MfaService mfaService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    private MfaMapper mapper;

    @PostMapping("/enable")
    public ResponseEntity<?> enableMfa(@RequestParam String username, String type) throws Exception {
        User user = userRepository.findByUsername(username).orElseThrow();
        user.setMfaEnabled(true);
        user.setMfaType(type);
        userRepository.save(user);
        return ResponseEntity.ok("MFA ativado com sucesso!");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyMfa(@RequestBody MfaRequest request) {
        if (mfaService.verifyMfaCode(request.getUsername(), request.getCode())) {
            return ResponseEntity.ok("User authenticated successfully!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid MFA code");
    }
}
