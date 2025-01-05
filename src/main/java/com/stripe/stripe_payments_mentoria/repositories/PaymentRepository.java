package com.stripe.stripe_payments_mentoria.repositories;

import com.stripe.stripe_payments_mentoria.commons.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository <Payment, Long > {

}
