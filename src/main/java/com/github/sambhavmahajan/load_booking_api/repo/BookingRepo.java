package com.github.sambhavmahajan.load_booking_api.repo;

import com.github.sambhavmahajan.load_booking_api.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;
@Repository
public interface BookingRepo extends JpaRepository<BookingEntity, UUID> {
    ArrayList<BookingEntity> findByLoad_ShipperId(String shipperId);
    ArrayList<BookingEntity> findByTransporterId(String transporterId);
}
