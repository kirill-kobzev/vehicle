package ru.home.vehicle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.vehicle.dto.VehicleDto;
import ru.home.vehicle.mapper.VehicleMapper;
import ru.home.vehicle.service.VehicleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto> getVehicleById(@PathVariable Integer id) {
        return ResponseEntity.ok(vehicleMapper.toDto(vehicleService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<VehicleDto> create(@RequestBody VehicleDto vehicleDto) {
        return ResponseEntity.ok(vehicleMapper.toDto(vehicleService.create(vehicleDto)));
    }

    @PutMapping
    public ResponseEntity<VehicleDto> update(@RequestBody VehicleDto vehicleDto) {
        return ResponseEntity.ok(vehicleMapper.toDto(vehicleService.update(vehicleDto)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VehicleDto> patch(@PathVariable Integer id, @RequestBody String vehicleDto) {
        return ResponseEntity.ok(vehicleMapper.toDto(vehicleService.update(id, vehicleDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        vehicleService.delete(id);
        return ResponseEntity.ok().build();
    }

}
