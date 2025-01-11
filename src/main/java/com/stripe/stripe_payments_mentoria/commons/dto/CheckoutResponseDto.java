package com.stripe.stripe_payments_mentoria.commons.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutResponseDto {
    private String urlPayment;
}
