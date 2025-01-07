package com.stripe.stripe_payments_mentoria.controllers.impl;

import com.stripe.stripe_payments_mentoria.commons.dto.AuthResponseDto;
import com.stripe.stripe_payments_mentoria.commons.dto.UserRequestDto;
import com.stripe.stripe_payments_mentoria.controllers.AuthApi;
import com.stripe.stripe_payments_mentoria.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<AuthResponseDto> createUser(UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.createUser(userRequestDto));
    }
}
