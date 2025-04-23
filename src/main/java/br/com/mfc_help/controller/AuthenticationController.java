package br.com.mfc_help.controller;

import br.com.mfc_help.domain.doctor.User;
import br.com.mfc_help.dto.auth.AuthRequestBody;
import br.com.mfc_help.dto.token.TokenResponse;
import br.com.mfc_help.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<Object> authenticate(@RequestBody @Valid AuthRequestBody authRequestBody){
        var token = new UsernamePasswordAuthenticationToken(authRequestBody.username(), authRequestBody.password());
        Authentication authentication = authenticationManager.authenticate(token);
        String tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenResponse(tokenJWT));
    }
}
