package com.drivehub.service;

import com.drivehub.model.dto.BookingRequest;
import com.drivehub.model.dto.BookingResponse;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface BookingService {
    @Nullable List<BookingResponse> getAllBookings();

    @Nullable BookingResponse approveBooking(Long bookingId);

    @Nullable BookingResponse rejectBooking(Long bookingId);

    @Nullable BookingResponse createBooking(@Valid BookingRequest request);

    @Nullable List<BookingResponse> getMyBookings();

    @Nullable String cancelBooking(Long bookingId);
}
