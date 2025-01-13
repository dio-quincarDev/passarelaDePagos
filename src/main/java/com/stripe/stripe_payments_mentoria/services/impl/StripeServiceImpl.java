package com.stripe.stripe_payments_mentoria.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.PriceListParams;
import com.stripe.param.ProductCreateParams;


import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.stripe_payments_mentoria.commons.dto.CheckoutRequestDto;
import com.stripe.stripe_payments_mentoria.commons.dto.CheckoutResponseDto;
import com.stripe.stripe_payments_mentoria.services.StripeService;
import com.stripe.stripe_payments_mentoria.strategy.StripeStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StripeServiceImpl implements StripeService {
    private final String endpointSecret;
    private final List<StripeStrategy> stripeStrategies;

    public StripeServiceImpl(@Value("${stripe.endpoint.secret}") String endpointSecret, List<StripeStrategy> stripeStrategies,
                             @Value("${stripe.secret.key}") String stripeKey) {
        Stripe.apiKey = stripeKey;
        this.endpointSecret = endpointSecret;
        this.stripeStrategies = Collections.unmodifiableList(stripeStrategies);
    }

    @Override
    public void manageWebhooks(Event event) {
        Optional.of(event)
                .map(this::processStrategy)
                .orElseThrow(()-> new RuntimeException("Error Processing Webhook"));
    }

    private Event processStrategy(Event event) {
        return stripeStrategies.stream()
                .filter(stripeStrategy -> stripeStrategy.isApplicable(event))
                .findFirst()
                .map(stripeStrategy -> stripeStrategy.process(event))
                .orElseGet(Event::new);
    }

    @Override
    public Event constructEvent(String payload, String stripeHeader) {
        try {
            return Webhook.constructEvent(payload, stripeHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer createCustomer(String email) {
        var customerCreateParams = CustomerCreateParams.builder()
                .setEmail(email)
                .build();
        try {
            return Customer.create(customerCreateParams);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product createProduct(String name) {
        var productCreateParams = ProductCreateParams.builder()
                .setName(name)
                .setType(ProductCreateParams.Type.SERVICE)
                .build();
        try {
            return Product.create(productCreateParams);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Price createPrice(String productId) {
        var createPrice = PriceCreateParams.builder()
                .setCurrency("usd")
                .setProduct(productId)
                .setUnitAmount(4000L)
                .build();
        try {
            return Price.create(createPrice);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public CheckoutResponseDto createCheckout(CheckoutRequestDto checkoutRequestDto) {
        var priceId = getPriceIdForProduct(checkoutRequestDto.getProductId());
        var session = SessionCreateParams.builder()
                .setCustomer(checkoutRequestDto.getCustomerId())
                .setSuccessUrl("http://localhost:8080")
                .setCancelUrl("http://localhost:8080")
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPrice((String) priceId)
                        .setQuantity(1L)
                        .build()
                )
                .putMetadata("metadata", String.valueOf(extraMetadata(checkoutRequestDto.getProductId())))
                .build();

        try {
            return Optional.of(Session.create(session))
                    .map(sessionCreated -> CheckoutResponseDto.builder()
                           .urlPayment(sessionCreated.getUrl())
                           .build())
                    .orElseThrow(() -> new RuntimeException("Error creating checkout"));
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> extraMetadata(String productId) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("productId", productId);
        return metadata;
    }

    private Object getPriceIdForProduct(String productId) {
        List<Price>prices = null;

        try {
            prices = Price.list(PriceListParams.builder().setProduct(productId).build()).getData();
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
        return prices.stream()
                .findFirst()
                .map(Price::getId)
                .orElseThrow(()-> new RuntimeException("Price not found"));
    }
}
