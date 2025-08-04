package com.plazoleta.plazoleta.plazoleta.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString(exclude = {"order", "dish"})
@EqualsAndHashCode(exclude = {"order", "dish"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_dishes")
public class OrderDishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id", nullable = false)
    private DishEntity dish;

    @Column(nullable = false)
    private Integer quantity;
}
