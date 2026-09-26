package br.com.pedro.auth.user.service;

import br.com.pedro.auth.user.dto.in.LoginDTO;
import br.com.pedro.auth.user.dto.in.RegisterDTO;
import br.com.pedro.auth.user.exception.ExistsByUsernameException;
import br.com.pedro.auth.user.model.UserModel;
import br.com.pedro.auth.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authManager;

    @Transactional
    public void register(RegisterDTO registerDTO) {
        log.info("[AuthenticationService] - Iniciando processo de registro de um novo usuário: {}", registerDTO.username());

        existsByUsername(registerDTO.username());
        var encryptedPassword = encryptPassword(registerDTO.password());

        var newUser = new UserModel(registerDTO.username(), encryptedPassword, registerDTO.role());
        var savedUser = userRepository.save(newUser);
        log.info("[AuthenticationService] - '{}' registrado com sucesso com id '{}'.", savedUser.getUsername(), savedUser.getId());
    }

    public void login(LoginDTO loginDTO) {
        log.info("[AuthenticationService] - Iniciando processo de login do usuário: {}", loginDTO.username());

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
        var authentication = authManager.authenticate(usernamePasswordAuthenticationToken);
    }

    private void existsByUsername(String username) {
        log.info("[AuthenticationService] - Verificando se o username {} já existe no banco de dados.", username);
        if (userRepository.existsByUsername(username)) {
            log.error("[AuthenticationService] - Username {} já existe no banco de dados.", username);
            throw new ExistsByUsernameException("Username já existe.");
        }
        log.info("[AuthenticationService] - Username {} não existe no banco de dados.", username);
    }

    private String encryptPassword(String password) {
        log.info("[AuthenticationService] - Iniciando processo de criptografia da senha.");

        var encryptedPassword = new BCryptPasswordEncoder().encode(password);

        log.info("[AuthenticationService] - Processo de criptografia da senha finalizado.");
        return encryptedPassword;
    }
}