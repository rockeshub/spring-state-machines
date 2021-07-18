package com.rockesh.ssm.services;

import com.rockesh.ssm.domain.Payment;
import com.rockesh.ssm.domain.PaymentEvent;
import com.rockesh.ssm.domain.PaymentState;
import org.springframework.statemachine.StateMachine;

/**
 * @author rakeshnc
 * @since Jul 18, 2021
 */
public interface PaymentService {
    Payment newPayment(Payment payment);
    StateMachine<PaymentState, PaymentEvent> preAuth(Long id);
    StateMachine<PaymentState, PaymentEvent> authorizePayment(Long id);

}
