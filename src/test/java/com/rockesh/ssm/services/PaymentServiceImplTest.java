package com.rockesh.ssm.services;

import com.rockesh.ssm.domain.Payment;
import com.rockesh.ssm.domain.PaymentEvent;
import com.rockesh.ssm.domain.PaymentState;
import com.rockesh.ssm.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author rakeshnc
 * @since Jul 18, 2021
 */
@Slf4j
@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentRepository paymentRepository;
    Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder().amount(new BigDecimal("12.99")).build();
    }

    @Transactional
    @Test
    void testPreAuth() {
        Payment savedPayment = paymentService.newPayment(payment);
        StateMachine<PaymentState, PaymentEvent> preAuthSm = paymentService.preAuth(savedPayment.getId());
        log.info("" + preAuthSm.getState().getId());
        Payment preAuthPayment = paymentRepository.getById(savedPayment.getId());
        log.info(preAuthPayment.toString());
        assertSame(savedPayment.getAmount(), preAuthPayment.getAmount());
    }

    @Transactional
    @RepeatedTest(10)
    void testAuth() {
        Payment savedPayment = paymentService.newPayment(payment);
        StateMachine<PaymentState, PaymentEvent> preAuthSm = paymentService.preAuth(savedPayment.getId());
        if (preAuthSm.getState().getId() == PaymentState.PRE_AUTH) {
            log.error("Payment is pre authorized");
            StateMachine<PaymentState, PaymentEvent> authorizePayment = paymentService.authorizePayment(savedPayment.getId());
            log.error("Result of Auth : "+ authorizePayment.getState().getId());
        } else {
            log.error("Payment failed while pre auth");
        }
        Payment preAuthPayment = paymentRepository.getById(savedPayment.getId());
        log.info(preAuthPayment.toString());
        assertSame(savedPayment.getAmount(), preAuthPayment.getAmount());
    }
}
