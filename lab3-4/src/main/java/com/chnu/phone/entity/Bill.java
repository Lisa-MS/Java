package com.chnu.phone.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bill_generator")
    @SequenceGenerator(name="bill_generator", sequenceName = "bill_seq", allocationSize=50)
    private Long id;

    private LocalDate payingPeriod;

    @OrderBy
    private LocalDateTime payedAt;

    private Double payment;

    @ManyToOne
    private User owner;

    @CreatedDate
    private LocalDateTime createdAt;
}
