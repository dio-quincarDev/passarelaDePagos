package com.stripe.stripe_payments_mentoria.commons.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    @Email
    @NotNull(message = "No email specified")
    private String email;
    @NotNull(message = "No has escrito la clave")
    private String password;
    @NotNull(message = "No has escrito el nombre")
    private String name;
}
