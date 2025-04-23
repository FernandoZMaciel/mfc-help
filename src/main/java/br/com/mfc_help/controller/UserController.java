package br.com.mfc_help.controller;

import br.com.mfc_help.domain.doctor.User;
import br.com.mfc_help.dto.user.UserPostRequestBody;
import br.com.mfc_help.dto.user.UserResponseBody;
import br.com.mfc_help.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseBody> post(@RequestBody @Valid UserPostRequestBody userPostRequestBody) {
        User savedUser = userService.save(userPostRequestBody);
        URI location = URI.create(String.format("/users/%s", savedUser.getId().toString()));

        return ResponseEntity.created(location).body(new UserResponseBody(savedUser));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseBody> get(@PathVariable String username) {
        return ResponseEntity.ok(new UserResponseBody(userService.findByUsername(username)));
    }

}
