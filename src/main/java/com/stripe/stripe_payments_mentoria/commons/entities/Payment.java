package com.stripe.stripe_payments_mentoria.commons.entities;

import com.stripe.stripe_payments_mentoria.commons.enums.StripeEventEnums;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String paymentIntentId;
    private String customerId;
    private String productId;
    private Long amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private StripeEventEnums type;
}
