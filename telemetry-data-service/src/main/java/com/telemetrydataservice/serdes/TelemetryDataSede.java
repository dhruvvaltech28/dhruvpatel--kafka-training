package com.telemetrydataservice.serdes;

import com.telemetrydataservice.dto.VehicleDTO;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class TelemetryDataSede extends Serdes.WrapperSerde<VehicleDTO>{

        public TelemetryDataSede() {
            super(new JsonSerializer<>(), new JsonDeserializer<>(VehicleDTO.class));
        }
    }

