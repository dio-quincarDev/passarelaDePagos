package com.stripe.stripe_payments_mentoria.controllers;

import com.stripe.stripe_payments_mentoria.commons.constants.ApiPathConstants;
import com.stripe.stripe_payments_mentoria.commons.dto.CheckoutRequestDto;
import com.stripe.stripe_payments_mentoria.commons.dto.CheckoutResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(ApiPathConstants.V1_ROUTE + ApiPathConstants.STRIPE_ROUTE)
public interface StripeApi {
    @PostMapping(value = "/webhook")
    ResponseEntity<Void>StripeWebhook(
            @RequestBody String payload,
            @RequestHeader ("Stripe-Signature") String stripeHeader
    );

    @PostMapping(value = "/checkout")
    ResponseEntity<CheckoutResponseDto> createCheckout(
            @RequestBody @Valid CheckoutRequestDto checkoutRequestDto
    );

}
