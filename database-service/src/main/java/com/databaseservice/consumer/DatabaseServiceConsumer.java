package com.databaseservice.consumer;

import com.databaseservice.entity.VehicleDTO;
import com.databaseservice.repo.TelemetryRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class DatabaseServiceConsumer {

  @Autowired
  private TelemetryRepo telemetryRepo;

  private final TelemetryRepo repository; // Assuming this is your repository
  private final ObjectMapper objectMapper = new ObjectMapper();

  public DatabaseServiceConsumer(TelemetryRepo repository) {
    this.repository = repository;

  }

  @KafkaListener(topics = "data-structured-telemetry", groupId = "my-group")
  public void consume(String message) {
    try {
      VehicleDTO telemetry = objectMapper.readValue(message, VehicleDTO.class);
      repository.save(telemetry); // Save to database
      System.out.println("Saved telemetry data: " + telemetry);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
