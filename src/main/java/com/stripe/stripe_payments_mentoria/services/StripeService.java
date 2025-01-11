package com.stripe.stripe_payments_mentoria.services;

import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.stripe_payments_mentoria.commons.dto.CheckoutRequestDto;
import com.stripe.stripe_payments_mentoria.commons.dto.CheckoutResponseDto;

public interface StripeService {
    void manageWebhooks(Event event);
    Event constructEvent(String payload, String stripeHeader );
    Customer createCustomer(String email);
    Product createProduct(String name);
    Price createPrice(String productId);
    CheckoutResponseDto createCheckout(CheckoutRequestDto checkoutRequestDto);
}
