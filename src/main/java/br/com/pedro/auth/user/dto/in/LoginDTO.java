package br.com.pedro.auth.user.dto.in;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "Username é obrigatório.")
        String username,
        @NotBlank(message = "Password é obrigatório.")
        String password
) {
}