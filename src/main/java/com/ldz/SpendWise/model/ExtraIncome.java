package com.ldz.SpendWise.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "extra_income", schema = "public")
public class ExtraIncome implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "amount")
    private Double amount;
    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

}
