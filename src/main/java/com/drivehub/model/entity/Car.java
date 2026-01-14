package com.drivehub.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private String carType;
    @Column(nullable = false)
    private String fuelType;
    @Column(nullable = false)
    private Integer seats;
    @Column(nullable = false)
    private String transmission;
    @Column(nullable = false)
    private Double pricePerDay;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarStatus status;

    private String imageUrl;
    private LocalDateTime createTime;

    @PrePersist
    public void onCreate() {
        createTime = LocalDateTime.now();
        if (status == null) status = CarStatus.AVAILABLE;
    }
}
