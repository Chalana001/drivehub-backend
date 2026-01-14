package com.drivehub.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingRequest {

    @NotNull
    private Long carId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
