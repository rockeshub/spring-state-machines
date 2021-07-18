package com.rockesh.ssm.config;

import com.rockesh.ssm.domain.PaymentEvent;
import com.rockesh.ssm.domain.PaymentState;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.util.Assert;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
class StateMachineConfigTest {

    @Autowired
    private StateMachineFactory<PaymentState, PaymentEvent> factory;
    private StateMachineConfig stateMachineConfigUnderTest;

    @BeforeEach
    void setUp() {
        stateMachineConfigUnderTest = new StateMachineConfig();
    }

    @Test
    void testNetStateMachine() throws Exception {
        // Setup
        StateMachine<PaymentState, PaymentEvent> sm = factory.getStateMachine(UUID.randomUUID());
        sm.start();
        log.info(sm.getState().toString());
        sm.sendEvent(PaymentEvent.PRE_AUTHORIZE);
        log.info(sm.getState().toString());
        sm.sendEvent(PaymentEvent.PRE_AUTH_DECLINED);
        //Assertions.assertEquals(sm.getState(), PaymentState.PRE_AUTH_ERROR);
        log.info(sm.getState().toString());
        // Verify the results
    }
}
