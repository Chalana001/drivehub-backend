package com.drivehub.service;

import com.drivehub.model.dto.CarRequest;
import com.drivehub.model.dto.CarResponse;
import com.drivehub.model.entity.Car;
import com.drivehub.model.entity.CarStatus;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    public final CarRepository carRepository;

    public CarServiceImpl (CarRepository carRepository){
        this.carRepository=carRepository;
    }

    @Override
    public @Nullable List<CarResponse> getAllCars() {
        return carRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public @Nullable CarResponse getCarById(Long id) {
        return null;
    }

    @Override
    public @Nullable CarResponse addCar(CarRequest carRequest) {
        Car car = Car.builder()
                .brand(carRequest.getBrand())
                .model(carRequest.getModel())
                .carType(carRequest.getCarType())
                .fuelType(carRequest.getFuelType())
                .seats(carRequest.getSeats())
                .transmission(carRequest.getTransmission())
                .pricePerDay(carRequest.getPricePerDay())
                .status(CarStatus.AVAILABLE)
                .imageUrl(carRequest.getImageUrl())
                .build();
        Car save = carRepository.save(car);
        return toResponse(save);
    }

    @Override
    public @Nullable CarResponse updateCar(Long id, CarRequest carRequest) {
        Car car = carRepository.findById(id).orElseThrow(()-> new RuntimeException("Car not found"));

        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setCarType(carRequest.getCarType());
        car.setFuelType(carRequest.getFuelType());
        car.setSeats(carRequest.getSeats());
        car.setTransmission(carRequest.getTransmission());
        car.setPricePerDay(carRequest.getPricePerDay());
        car.setImageUrl(carRequest.getImageUrl());

        return toResponse(carRepository.save(car));
    }

    @Override
    public @Nullable CarResponse updateStatus(Long id, CarStatus carStatus) {
        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
        car.setStatus(carStatus);
        return toResponse(carRepository.save(car));
    }

    @Override
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Car not found");
        }
        carRepository.deleteById(id);
    }

    private CarResponse toResponse(Car car) {
        return CarResponse.builder()
                .carId(car.getCarId())
                .brand(car.getBrand())
                .model(car.getModel())
                .carType(car.getCarType())
                .fuelType(car.getFuelType())
                .seats(car.getSeats())
                .transmission(car.getTransmission())
                .pricePerDay(car.getPricePerDay())
                .status(car.getStatus())
                .imageUrl(car.getImageUrl())
                .build();
    }
}
