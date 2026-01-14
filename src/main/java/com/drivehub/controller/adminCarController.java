package com.drivehub.controller;

import com.drivehub.model.dto.CarRequest;
import com.drivehub.model.dto.CarResponse;
import com.drivehub.model.entity.CarStatus;
import com.drivehub.service.CarService;
import com.drivehub.service.CarServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/cars")
@CrossOrigin
public class adminCarController {
    private final CarService carService;

    public adminCarController (CarService carService){
        this.carService=carService;
    }

    @PostMapping
    public ResponseEntity<CarResponse> addCar(@Valid @RequestBody CarRequest carRequest){
        System.out.println(carRequest.getCarType());
        return ResponseEntity.ok(carService.addCar(carRequest));
    }

    @PutMapping
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long id,@Valid @RequestBody CarRequest carRequest){
        return ResponseEntity.ok(carService.updateCar(id, carRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Long id){
        carService.deleteCar(id);
        return ResponseEntity.ok("Car Deleted");
    }

    @PutMapping ("/{id}/status")
    public ResponseEntity<CarResponse> updateStatus(@PathVariable Long id, @RequestParam CarStatus carStatus){
        return ResponseEntity.ok(carService.updateStatus(id, carStatus));
    }

}
