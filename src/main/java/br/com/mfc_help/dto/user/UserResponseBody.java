package br.com.mfc_help.dto.user;

import br.com.mfc_help.domain.doctor.User;

import java.util.UUID;

public record UserResponseBody(
        UUID id,
        String username,
        String name,
        String gender

) {
    public UserResponseBody(User user){
        this(user.getId(), user.getUsername(), user.getName(), user.getGender().toString());
    }
}
