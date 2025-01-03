package com.stripe.stripe_payments_mentoria.strategy;

import com.stripe.model.Event;

public interface StripeStrategy {
    boolean isApplicable(Event event);
    Event process(Event event);
}
