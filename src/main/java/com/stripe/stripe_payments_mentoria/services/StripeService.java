package com.stripe.stripe_payments_mentoria.services;

import com.stripe.model.Event;

public interface StripeService {
    void manageWebhooks(Event event);
    Event constructEvent(String payload, String stripeHeader );

}
