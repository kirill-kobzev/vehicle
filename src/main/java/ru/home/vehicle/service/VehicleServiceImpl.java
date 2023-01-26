package ru.home.vehicle.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.home.vehicle.dto.VehicleDto;
import ru.home.vehicle.entity.Vehicle;
import ru.home.vehicle.mapper.VehicleMapper;
import ru.home.vehicle.repository.VehicleRepository;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final ObjectMapper objectMapper;

    @Override
    public Vehicle findById(Integer id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new UnsupportedOperationException("Машина не найдена"));
    }

    @Override
    public Vehicle create(VehicleDto vehicleDto) {
        checkCreateVehicle(vehicleDto);
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDto);
        if (!CollectionUtils.isEmpty(vehicle.getDrivers())) {
            vehicle.getDrivers().forEach(driver -> driver.setVehicle(vehicle));
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle update(VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleRepository
                .findById(vehicleDto.getId())
                .orElseThrow(() -> new UnsupportedOperationException("Машина не найдена"));
        vehicle.setColor(vehicleDto.getColor());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setLicensePlate(vehicle.getLicensePlate());
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle update(Integer id, String json) {
        Vehicle vehicle = vehicleRepository
                .findById(id)
                .orElseThrow(() -> new UnsupportedOperationException("Машина не найдена"));
        VehicleDto oldVehicleDto = vehicleMapper.toDto(vehicle);
        VehicleDto newVehicleDto;
        try {
            ObjectReader updateObject = objectMapper.readerForUpdating(oldVehicleDto);
            newVehicleDto = updateObject.readValue(json);
        } catch (IOException ex) {
            throw new UnsupportedOperationException("Не удалось обновить объект: " + id);
        }
        newVehicleDto.setId(id);
        return vehicleRepository.save(vehicleMapper.toEntity(newVehicleDto));
    }

    @Override
    public void delete(Integer id) {
        vehicleRepository.deleteById(id);
    }

    private void checkCreateVehicle(VehicleDto vehicleDto) {
        if (vehicleDto.getId() != null) {
            throw new UnsupportedOperationException("Попытка сохранить или присвоить id машине");
        }
        if (!CollectionUtils.isEmpty(vehicleDto.getDrivers()) &&
            vehicleDto.getDrivers().stream().anyMatch(driverDto -> driverDto.getId() != null)) {
            throw new UnsupportedOperationException("Попытка сохранить или присвоить id водителю");
        }
        if (!CollectionUtils.isEmpty(vehicleDto.getCompanies()) &&
            vehicleDto.getCompanies().stream().anyMatch(companyDto -> companyDto.getId() != null)) {
            throw new UnsupportedOperationException("Попытка сохранить или присвоить id компании");
        }
    }
}
