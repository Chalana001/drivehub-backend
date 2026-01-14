package com.drivehub.model.dto;

import com.drivehub.entity.BookingStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {

    private Long bookingId;

    private Long carId;
    private String carBrand;
    private String carModel;

    private LocalDate startDate;
    private LocalDate endDate;

    private Integer totalDays;
    private Double totalAmount;

    private BookingStatus status;
}
