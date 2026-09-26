package br.com.pedro.auth.user.controller;

import br.com.pedro.auth.user.dto.in.LoginDTO;
import br.com.pedro.auth.user.dto.in.RegisterDTO;
import br.com.pedro.auth.user.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO registerDTO) {
        log.info("[AuthenticationController] Requisição recebida para registrar usuário: {}", registerDTO.username());
        authenticationService.register(registerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginDTO loginDTO) {
        log.info("[AuthenticationController] Requisição recebida para login de usuário: {}", loginDTO.username());
        String token = authenticationService.login(loginDTO);
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}