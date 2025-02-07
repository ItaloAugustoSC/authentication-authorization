package com.italoasc.authentication_authorization.service;

import com.italoasc.authentication_authorization.entity.User;
import com.italoasc.authentication_authorization.model.UserRegisterRequest;
import com.italoasc.authentication_authorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.italoasc.authentication_authorization.mapper.UserMapper.userMapper;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList())
        );
    }

    public String register(UserRegisterRequest userRegisterRequest) {
//        if (user.isMfaEnabled()){
//            if (user.getMfaType().equals("EMAIL")){
//                user.setMfaType(MfaType.EMAIL);
//            } else if (user.getMfaType().equals("SMS")) {
//                user.setMfaType(MfaType.SMS);
//            }
//        }
        User user = userMapper.map(userRegisterRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Usuário registrado com sucesso!";
    }

    public User findUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow();
    }
}

