package br.com.pedro.auth.user.dto.in;

import br.com.pedro.auth.user.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @NotBlank(message = "Username é obrigatório.")
        @Size(max = 50, message = "Username deve ter no máximo 50 caracteres.")
        String username,
        @NotBlank(message = "Password é obrigatório.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).+$",
                message = "Password deve conter pelo menos 1 letra minúscula, 1 maiúscula, 1 número e 1 caractere especial."
        )
        @Size(min = 8, max = 15, message = "Password deve ter entre 8 e 15 caracteres.")
        String password,
        @NotNull(message = "Role é obrigatório.")
        UserRole role
) {
}