package br.com.mfc_help.service;

import br.com.mfc_help.domain.doctor.User;
import br.com.mfc_help.dto.user.UserPostRequestBody;
import br.com.mfc_help.infra.exception.BadRequestException;
import br.com.mfc_help.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User save(UserPostRequestBody userPostRequestBody) {
        Optional<User> byUsername = userRepository.findByUsername(userPostRequestBody.username());
        if (byUsername.isPresent()) {
            throw new BadRequestException("Username already exists");
        }

        return userRepository.save(new User(
                null,
                userPostRequestBody.username(),
                passwordEncoder.encode(userPostRequestBody.password()),
                userPostRequestBody.name(),
                userPostRequestBody.gender(),
                null
        ));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
