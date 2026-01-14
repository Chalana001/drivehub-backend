package com.drivehub.repository;

import com.drivehub.entity.Booking;
import com.drivehub.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUser_UserId(Long userId);

    // 🔥 Overlap check:
    // if existing booking overlaps requested dates, return list (not empty => not available)
    @Query("""
        SELECT b FROM Booking b
        WHERE b.car.carId = :carId
          AND b.status IN (:activeStatuses)
          AND (b.startDate <= :endDate AND b.endDate >= :startDate)
    """)
    List<Booking> findOverlappingBookings(
            @Param("carId") Long carId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("activeStatuses") List<BookingStatus> activeStatuses
    );
}
