package com.telemetrydataservice.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemetrydataservice.dto.VehicleDTO;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class VehicleDeserializer implements Deserializer<VehicleDTO> {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
            // Not needed for this example
        }

        @Override
        public VehicleDTO deserialize(String topic, byte[] data) {
            try {
                if (data == null) {
                    return null;
                }
                return objectMapper.readValue(data, VehicleDTO.class);
            } catch (Exception e) {
                throw new RuntimeException("Error deserializing User", e);
            }
        }

        @Override
        public void close() {
            // Not needed for this example
        }
}