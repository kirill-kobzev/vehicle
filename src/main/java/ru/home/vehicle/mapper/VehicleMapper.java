package ru.home.vehicle.mapper;


import org.mapstruct.Mapper;
import ru.home.vehicle.dto.VehicleDto;
import ru.home.vehicle.entity.Vehicle;


@Mapper(componentModel = "spring", uses = {})
public interface VehicleMapper extends EntityMapper<VehicleDto, Vehicle> {

    @Override
    Vehicle toEntity(VehicleDto dto);

    @Override
    VehicleDto toDto(Vehicle entity);

}
