package com.stripe.stripe_payments_mentoria.strategy.impl;

import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.stripe_payments_mentoria.commons.entities.Payment;
import com.stripe.stripe_payments_mentoria.commons.enums.StripeEventEnums;
import com.stripe.stripe_payments_mentoria.repositories.PaymentRepository;
import com.stripe.stripe_payments_mentoria.strategy.StripeStrategy;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StripeStrategyCheckoutSessionCompleted implements StripeStrategy {
    private final PaymentRepository paymentRepository;

    public StripeStrategyCheckoutSessionCompleted(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public boolean isApplicable(Event event) {
        return StripeEventEnums.CHECKOUT_SESSION_COMPLETED.value.equals(event.getType());
    }

    @Override
    public Event process(Event event) {
        var session = this.deserealize(event);
        return Optional.of(event)
                .map(givenEvent-> paymentRepository.findByPaymentIntentId(session.getPaymentIntent()))
                .map(payment-> setProductId(payment, session.getMetadata().get("product id")))
                .map(paymentRepository::save)
                .map(given-> event)
                .orElseThrow(() -> new RuntimeException("Error Processing"));
    }

    private Payment setProductId(Payment payment, String productId) {
        payment.setProductId(productId);
        payment.setType(StripeEventEnums.CHECKOUT_SESSION_COMPLETED);
        return payment;
    }

    private Session deserealize(Event event) {
        return (Session) event.getDataObjectDeserializer().getObject()
                .orElseThrow(() -> new RuntimeException("Error deserializing"));
    }
}
