package com.datatrasferservice.controller;


import com.datatrasferservice.dto.VehicleDTO;
import com.datatrasferservice.service.DataTransferService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("/telemetry")
@RequiredArgsConstructor()
public class DataTransferController {

  private final DataTransferService dataTransferServiceService;

  @PostMapping(value = "/sendData")
  public ResponseEntity<Object> dataReceive() {

    File file = new File("/Users/dhruvpatel/Documents/personal/cars.csv");

    Object message = null;
    try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
      String[] nextLine;
      boolean isFirstLine = true;

      while ((nextLine = csvReader.readNext()) != null) {
        /*if (isFirstLine) {
          isFirstLine = false; // Skip header line
          continue;
        }*/


        VehicleDTO vehicle = new VehicleDTO();
        vehicle.setId(nextLine[0]);
        vehicle.setUserName(nextLine[1]);
        vehicle.setVehicleNumber(nextLine[2]);
        vehicle.setVin(nextLine[3]);
        vehicle.setSpeed(nextLine[4]);
        vehicle.setVehicleName(nextLine[5]);
        vehicle.setVehicleType(nextLine[6]);
        vehicle.setEngineOn(nextLine[7]);
        vehicle.setEngineOff(nextLine[8]);
        vehicle.setHarshBreaking(nextLine[9]);
        vehicle.setHarshAcceleration(nextLine[10]);
        vehicle.setEngineCheck(nextLine[11]);
        vehicle.setTyreCheck(nextLine[12]);
        System.out.println(vehicle.getUserName());

        message= dataTransferServiceService.produce(vehicle);

        ///vehicles.add(vehicle);
      }
    } catch (IOException  e) {
      System.out.println("IO Exception");
      e.printStackTrace();
    } catch (CsvValidationException e) {
      System.out.println("IO Exception");
        throw new RuntimeException(e);
    }


      // VehicleDTO vehicleDTO = new VehicleDTO();
    //vehicleDTO.setId(1L);
    //vehicleDTO.setVehicleName("skoda");
    //vehicleDTO.setVehicleType("suv");
    //vehicleDTO.setVehicleNumber("GJ08CM2056");
    //vehicleDTO.setVin("123456789098765");
    //vehicleDTO.setSpeed(80L);
    //vehicleDTO.setUserName("dhruv")

    return ResponseEntity.accepted().body(message);
  }

}
