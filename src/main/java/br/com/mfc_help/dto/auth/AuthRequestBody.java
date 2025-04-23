package br.com.mfc_help.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestBody(
        @NotBlank(message = "The Username cannot be blank!") String username,
        @NotBlank(message = "The Password cannot be blank!") String password
) {
}
