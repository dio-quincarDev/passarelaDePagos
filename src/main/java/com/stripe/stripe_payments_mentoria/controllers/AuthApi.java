package com.stripe.stripe_payments_mentoria.controllers;

import com.stripe.stripe_payments_mentoria.commons.constants.ApiPathConstants;
import com.stripe.stripe_payments_mentoria.commons.dto.AuthResponseDto;
import com.stripe.stripe_payments_mentoria.commons.dto.UserRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(ApiPathConstants.V1_ROUTE + ApiPathConstants.AUTH_ROUTE)
public interface AuthApi {
    @PostMapping
    ResponseEntity<AuthResponseDto> createUser(@RequestBody  @Valid UserRequestDto userRequestDto);
}
