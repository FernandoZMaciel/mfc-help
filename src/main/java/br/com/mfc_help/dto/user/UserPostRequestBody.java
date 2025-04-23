package br.com.mfc_help.dto.user;

import br.com.mfc_help.domain.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.aspectj.weaver.ast.Not;

public record UserPostRequestBody(
        @NotBlank(message = "The Username cannot be Blank")
        @Size(min = 8, message = "The Username must have at least 8 characters")
        String username,
        @NotBlank(message = "The Password cannot be Blank")
        @Pattern( regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+{}:;<>,.?~\\[\\]-])(?=.*[A-Z])(?=.*[a-z]).{8,}$",
                message = "The Password must have at least 8 characters, 1 special, 1 number, 1 uppercase and 1 lowercase"
        )
        String password,
        @NotBlank(message = "The Name cannot be Blank")
        String name,
        Gender gender
) {
}
