package com.drivehub.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarRequest {

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    private String carType;

    @NotBlank
    private String fuelType;

    @NotNull
    @Min(2)
    private Integer seats;

    @NotBlank
    private String transmission;

    @NotNull
    @Min(1)
    private Double pricePerDay;

    private String imageUrl;
}
