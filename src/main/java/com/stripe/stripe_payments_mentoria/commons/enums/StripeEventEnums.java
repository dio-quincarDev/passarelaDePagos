package com.stripe.stripe_payments_mentoria.commons.enums;

public enum StripeEventEnums {
    PAYMENT_INTENT_SUCCEED("payment_intent.succeeded"),
    CHECKOUT_SESSION_COMPLETED("checkout_session.completed");

    public final String value;

    StripeEventEnums(String value) {
        this.value = value;
    }
}
