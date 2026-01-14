package com.drivehub.model.dto;

import com.drivehub.model.entity.CarStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarResponse {
    private Long carId;
    private String brand;
    private String model;
    private String carType;
    private String fuelType;
    private Integer seats;
    private String transmission;
    private Double pricePerDay;
    private CarStatus status;
    private String imageUrl;
}
