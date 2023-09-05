package com.ldz.SpendWise.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cost", schema = "public")
public class Cost implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = CostCategory.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "cost_category_id")
    private CostCategory costCategory;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
