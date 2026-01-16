package com.drivehub.service;

import com.drivehub.entity.BookingStatus;
import com.drivehub.model.dto.BookingRequest;
import com.drivehub.model.dto.BookingResponse;
import com.drivehub.model.entity.Car;
import com.drivehub.repository.BookingRepository;
import com.drivehub.repository.CarRepository;
import com.drivehub.repository.UserRepository;
import com.drivehub.security.SecurityUtil;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import com.drivehub.entity.Booking;
import com.drivehub.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{

    public final BookingRepository bookingRepository;
    public final CarRepository carRepository;
    public final UserRepository userRepository;

    public BookingServiceImpl (BookingRepository bookingRepository, CarRepository carRepository, UserRepository userRepository){
        this.bookingRepository = bookingRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @Override
    public @Nullable List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public @Nullable BookingResponse approveBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()-> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.APPROVED);
        return toResponse(bookingRepository.save(booking));
    }

    @Override
    public @Nullable BookingResponse rejectBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()-> new RuntimeException("Booking not found"));
        booking.setStatus(BookingStatus.REJECTED);
        return toResponse(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public @Nullable BookingResponse createBooking(BookingRequest request) {
        if (request.getEndDate().isBefore(request.getStartDate())){
            throw new RuntimeException("End date cannot be before start date");
        }

        Car car = carRepository.lockCarById(request.getCarId());
        if (car == null) {
            throw new RuntimeException("Car not found");
        }

        List<BookingStatus> activeStatuses = List.of(BookingStatus.PENDING, BookingStatus.APPROVED);

        boolean hasOverlap = !bookingRepository.findOverlappingBookings(
                car.getCarId(),
                request.getStartDate(),
                request.getEndDate(),
                activeStatuses
        ).isEmpty();

        if (hasOverlap) {
            throw new RuntimeException("Car is not available for selected dates");
        }

        // get logged user
        String email = SecurityUtil.getLoggedInEmail();
        User user = userRepository.findByEmail(email).orElseThrow();

        long days = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate()) + 1;
        double total = days * car.getPricePerDay();

        Booking booking = Booking.builder()
                .user(user)
                .car(car)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .totalDays((int) days)
                .totalAmount(total)
                .status(BookingStatus.PENDING)
                .build();

        Booking saved = bookingRepository.save(booking);
        return toResponse(saved);

    }

    @Override
    public @Nullable List<BookingResponse> getMyBookings() {
        String email = SecurityUtil.getLoggedInEmail();
        User user = userRepository.findByEmail(email).orElseThrow();

        return bookingRepository.findByUser_UserId(user.getUserId())
                .stream().map(this::toResponse).toList();
    }

    @Override
    public @Nullable String cancelBooking(Long bookingId) {
        String email = SecurityUtil.getLoggedInEmail();
        User user = userRepository.findByEmail(email).orElseThrow();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("You cannot cancel someone else's booking");
        }

        if (booking.getStatus() == BookingStatus.APPROVED) {
            throw new RuntimeException("Approved booking cannot be cancelled (admin required)");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        return "Booking cancelled";
    }

    private BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .carId(booking.getCar().getCarId())
                .carBrand(booking.getCar().getBrand())
                .carModel(booking.getCar().getModel())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .totalDays(booking.getTotalDays())
                .totalAmount(booking.getTotalAmount())
                .status(booking.getStatus())
                .build();
    }
}
