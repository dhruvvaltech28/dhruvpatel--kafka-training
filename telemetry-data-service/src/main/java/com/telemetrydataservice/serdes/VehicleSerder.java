package com.telemetrydataservice.serdes;

import com.telemetrydataservice.dto.VehicleDTO;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class VehicleSerder implements Serde<VehicleDTO> {

        @Override
        public Serializer<VehicleDTO> serializer() {
            return new VehicleSerializer();
        }

        @Override
        public Deserializer<VehicleDTO> deserializer() {
            return new VehicleDeserializer();
        }
    }