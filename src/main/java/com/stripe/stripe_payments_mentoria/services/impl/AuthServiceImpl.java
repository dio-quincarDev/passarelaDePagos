package com.stripe.stripe_payments_mentoria.services.impl;

import com.stripe.stripe_payments_mentoria.commons.dto.AuthResponseDto;
import com.stripe.stripe_payments_mentoria.commons.dto.UserRequestDto;
import com.stripe.stripe_payments_mentoria.commons.entities.UserModel;
import com.stripe.stripe_payments_mentoria.repositories.UserRepository;
import com.stripe.stripe_payments_mentoria.services.AuthService;
import com.stripe.stripe_payments_mentoria.services.StripeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final StripeService stripeService;
    private final UserRepository userRepository;

    public AuthServiceImpl( StripeService stripeService, UserRepository userRepository) {
        this.stripeService = stripeService;
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponseDto createUser(UserRequestDto userRequestDto) {
        return Optional.of(userRequestDto)
                .map(this::mapToEntity)
                .map(this::setUserCustomerAndProduct)
                .map(userRepository::save)
                .map(usermodel-> AuthResponseDto.builder()
                        .customerId(usermodel.getCustomerId())
                        .productId(usermodel.getProductId())
                        .build())
                .orElseThrow(()-> new RuntimeException("Error creating user"));
    }

    private UserModel setUserCustomerAndProduct(UserModel userModel) {
        var customerCreated = stripeService.createCustomer(userModel.getEmail());
        var productCreated = stripeService.createProduct(userModel.getName());

        stripeService.createPrice(productCreated.getId());

        userModel.setProductId(productCreated.getId());
        userModel.setCustomerId(customerCreated.getId());

        return userModel;
    }

    private UserModel mapToEntity(UserRequestDto userRequestDto) {
        return UserModel.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .build();
    }
}
