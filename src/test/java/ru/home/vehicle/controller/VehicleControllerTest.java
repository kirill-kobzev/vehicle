package ru.home.vehicle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.home.vehicle.configuration.TestContainer;
import ru.home.vehicle.dto.CompanyDto;
import ru.home.vehicle.dto.DriverDto;
import ru.home.vehicle.dto.VehicleDto;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest extends TestContainer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;

    @Test
    void findById() throws Exception {
        String resultJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicles/1"))
                                   .andDo(print())
                                   .andReturn()
                                   .getResponse()
                                   .getContentAsString();

        VehicleDto vehicleDto = objectMapper.readValue(resultJson, VehicleDto.class);
        Assertions.assertEquals("Белый", vehicleDto.getColor());
    }

    @Test
    void create() throws Exception {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setColor("Красный");
        vehicleDto.setModel("AUDI");
        vehicleDto.setModel("A001AA93");

        CompanyDto companyDto = new CompanyDto();
        companyDto.setName("Компания и Ко");

        DriverDto driverOne = new DriverDto();
        driverOne.setName("Водитель 1");

        DriverDto driverTwo = new DriverDto();
        driverOne.setName("Водитель 2");

        vehicleDto.setCompanies(List.of(companyDto));
        vehicleDto.setDrivers(List.of(driverOne, driverTwo));

        String resultJson = mockMvc.perform(
                                           post("/api/v1/vehicles")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(objectMapper.writeValueAsString(vehicleDto))
                                   )
                                   .andDo(print())
                                   .andReturn()
                                   .getResponse()
                                   .getContentAsString();

        VehicleDto createdVehicle = objectMapper.readValue(resultJson, VehicleDto.class);

        Assertions.assertNotNull(createdVehicle.getCompanies());
        Assertions.assertNotNull(createdVehicle.getDrivers());
        Assertions.assertEquals("Красный", createdVehicle.getColor());
        Assertions.assertEquals(2, createdVehicle.getDrivers().size());

    }
}
