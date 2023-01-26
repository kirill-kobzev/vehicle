package ru.home.vehicle.service;

import ru.home.vehicle.dto.VehicleDto;
import ru.home.vehicle.entity.Vehicle;


public interface VehicleService {

    Vehicle findById(Integer id);

    Vehicle create(VehicleDto vehicleDto);

    Vehicle update(VehicleDto vehicleDto);

    Vehicle update(Integer id, String json);

    void delete(Integer id);

}
