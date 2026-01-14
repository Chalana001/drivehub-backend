package com.drivehub.controller;

import com.drivehub.model.dto.CarResponse;
import com.drivehub.service.CarService;
import com.drivehub.service.CarServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@CrossOrigin
public class CarController {
    private final CarService carService;

    public CarController (CarService carService){
        this.carService=carService;
    }

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars(){
        return ResponseEntity.ok(carService.getAllCars());
    }

    public ResponseEntity<CarResponse> getCar(@PathVariable Long id){
        return ResponseEntity.ok(carService.getCarById(id));
    }
}
