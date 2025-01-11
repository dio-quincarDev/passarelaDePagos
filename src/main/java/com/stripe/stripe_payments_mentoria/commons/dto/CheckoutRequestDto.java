package com.stripe.stripe_payments_mentoria.commons.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutRequestDto {
    @NotNull
    private String customerId;

    @NotNull
    private String productId;

}
