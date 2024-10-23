package com.telemetrydataservice.process;

import com.telemetrydataservice.dto.VehicleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TelemetryProducer {

    @Autowired
    private KafkaTemplate<String, VehicleDTO> kafkaTemplate;

    public void sendToStructuredTopic(VehicleDTO data) {
        kafkaTemplate.send("data-structured-telemetry", data);
    }
}