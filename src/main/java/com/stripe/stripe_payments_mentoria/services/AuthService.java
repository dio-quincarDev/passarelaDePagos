package com.stripe.stripe_payments_mentoria.services;

import com.stripe.stripe_payments_mentoria.commons.dto.AuthResponseDto;
import com.stripe.stripe_payments_mentoria.commons.dto.UserRequestDto;

public interface AuthService {
    AuthResponseDto createUser(UserRequestDto userRequestDto);
}
