package com.rockesh.ssm.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author rakeshnc
 * @since Jul 18, 2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Payment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentState state;

    private BigDecimal amount;
}
