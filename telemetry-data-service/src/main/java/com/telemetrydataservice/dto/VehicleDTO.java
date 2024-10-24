package com.telemetrydataservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleDTO {
    private String id;
    private String userName;
    private String vehicleNumber;
    private String vin;
    private String speed;
    private String vehicleName;
    private String vehicleType;
    private String engineOn;
    private String engineOff;
    private String harshBreaking;
    private String harshAcceleration;
    private String engineCheck;
    private String tyreCheck;
    private String alertMessage;
}
