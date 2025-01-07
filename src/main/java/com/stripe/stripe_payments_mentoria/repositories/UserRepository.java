package com.stripe.stripe_payments_mentoria.repositories;

import com.stripe.stripe_payments_mentoria.commons.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <UserModel, Long> {
}
