package br.com.pedro.auth.user.service;

import br.com.pedro.auth.user.exception.JWTValidationException;
import br.com.pedro.auth.user.model.UserModel;
import br.com.pedro.auth.user.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository;
    private final String secretKey = "mySecretKey";

    public String generateToken(UserModel user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT
                    .create()
                    .withSubject(user.getUsername())
                    .withIssuer("auth-service")
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            return null;
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT
                    .require(algorithm)
                    .withIssuer("auth-service")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTValidationException("Invalid token");
        }
    }
}