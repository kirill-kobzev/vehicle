package ru.home.vehicle.dto;

import lombok.Data;

import java.util.List;

@Data
public class VehicleDto {

    private Integer id;

    private String licensePlate;

    private String color;

    private String model;

    private List<CompanyDto> companies;

    private List<DriverDto> drivers;
}
