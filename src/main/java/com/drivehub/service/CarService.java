package com.drivehub.service;

import com.drivehub.model.dto.CarRequest;
import com.drivehub.model.dto.CarResponse;
import com.drivehub.model.entity.CarStatus;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface CarService {
    @Nullable List<CarResponse> getAllCars();

    @Nullable CarResponse getCarById(Long id);

    @Nullable CarResponse addCar(@Valid CarRequest carRequest);

    @Nullable CarResponse updateCar(Long id, @Valid CarRequest carRequest);

    @Nullable CarResponse updateStatus(Long id, CarStatus carStatus);

    void deleteCar(Long id);
}
