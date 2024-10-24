package com.databaseservice.repo;

import com.databaseservice.entity.VehicleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetryRepo extends JpaRepository<VehicleDTO,String> {
}
