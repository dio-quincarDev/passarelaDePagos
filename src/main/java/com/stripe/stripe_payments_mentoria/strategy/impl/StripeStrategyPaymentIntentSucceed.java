package com.stripe.stripe_payments_mentoria.strategy.impl;

import com.stripe.model.Event;
import com.stripe.stripe_payments_mentoria.commons.enums.StripeEventEnums;
import com.stripe.stripe_payments_mentoria.strategy.StripeStrategy;
import org.springframework.stereotype.Component;

@Component
public class StripeStrategyPaymentIntentSucceed implements StripeStrategy {
    @Override
    public boolean isApplicable(Event event) {
        return StripeEventEnums.PAYMENT_INTENT_SUCCEED.value.equals(event.getType());
    }

    @Override
    public Event process(Event event) {
        return null;
    }
}
