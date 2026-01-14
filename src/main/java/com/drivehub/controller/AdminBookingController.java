package com.drivehub.controller;

import com.drivehub.model.dto.BookingResponse;
import com.drivehub.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/bookings")
@CrossOrigin
public class AdminBookingController {

    private final BookingService bookingService;

    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> allBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @PutMapping("/{bookingId}/approve")
    public ResponseEntity<BookingResponse> approve(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.approveBooking(bookingId));
    }

    @PutMapping("/{bookingId}/reject")
    public ResponseEntity<BookingResponse> reject(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.rejectBooking(bookingId));
    }
}
