package com.telemetrydataservice.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemetrydataservice.dto.VehicleDTO;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class VehicleSerializer implements Serializer<VehicleDTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Not needed for this example
    }

    @Override
    public byte[] serialize(String topic, VehicleDTO data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing User", e);
        }
    }

    @Override
    public void close() {
        // Not needed for this example
    }
}
