package com.databaseservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class VehicleDTO {
        @Id
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