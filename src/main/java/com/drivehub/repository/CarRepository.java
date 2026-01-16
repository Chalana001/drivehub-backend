package com.drivehub.repository;

import com.drivehub.model.entity.Car;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import com.drivehub.model.entity.Car;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Car c WHERE c.carId = :id")
    Car lockCarById(@Param("id") Long id);
}
