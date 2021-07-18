package com.rockesh.ssm.repository;

import com.rockesh.ssm.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author rakeshnc
 * @since Jul 18, 2021
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
