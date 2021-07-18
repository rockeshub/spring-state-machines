package com.rockesh.ssm.services;

import com.rockesh.ssm.domain.Payment;
import com.rockesh.ssm.domain.PaymentEvent;
import com.rockesh.ssm.domain.PaymentState;
import com.rockesh.ssm.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author rakeshnc
 * @since Jul 18, 2021
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class PaymentStateChangeInterceptor extends StateMachineInterceptorAdapter<PaymentState, PaymentEvent> {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void preStateChange(State<PaymentState, PaymentEvent> state, Message<PaymentEvent> message, Transition<PaymentState,
            PaymentEvent> transition, StateMachine<PaymentState, PaymentEvent> stateMachine, StateMachine<PaymentState, PaymentEvent> rootStateMachine) {
        Optional.ofNullable(message).flatMap(msg -> Optional.ofNullable(
                (Long) msg.getHeaders().getOrDefault(PaymentServiceImpl.PAYMENT_ID_HEADER, -1L)))
                .ifPresent(paymentId -> {
                    log.info("Sate changes"+  state.getId());
            Payment payment = paymentRepository.getById(paymentId);
            payment.setState(state.getId());
            paymentRepository.save(payment);
        });
    }
}
