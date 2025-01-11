package com.stripe.stripe_payments_mentoria.controllers.impl;

import com.stripe.stripe_payments_mentoria.commons.dto.CheckoutRequestDto;
import com.stripe.stripe_payments_mentoria.commons.dto.CheckoutResponseDto;
import com.stripe.stripe_payments_mentoria.controllers.StripeApi;
import com.stripe.stripe_payments_mentoria.services.StripeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StripeController implements StripeApi {
    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @Override
    public ResponseEntity<Void> StripeWebhook(String payload, String stripeHeader) {
        var event = stripeService.constructEvent(payload, stripeHeader);
        stripeService.manageWebhooks(event);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CheckoutResponseDto> createCheckout(CheckoutRequestDto checkoutRequestDto) {
        return ResponseEntity.ok(stripeService.createCheckout(checkoutRequestDto));
    }
}
