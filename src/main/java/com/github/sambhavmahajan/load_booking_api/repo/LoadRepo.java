package com.github.sambhavmahajan.load_booking_api.repo;

import com.github.sambhavmahajan.load_booking_api.entities.LoadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface LoadRepo extends JpaRepository<LoadEntity, UUID> {
    ArrayList<LoadEntity> findByShipperId(String shipperId);
    ArrayList<LoadEntity> findByTruckType(String truckType);
}
