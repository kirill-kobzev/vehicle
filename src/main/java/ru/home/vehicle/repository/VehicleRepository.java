package ru.home.vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.vehicle.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}
