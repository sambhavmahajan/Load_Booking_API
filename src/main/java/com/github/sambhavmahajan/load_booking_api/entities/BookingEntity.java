package com.github.sambhavmahajan.load_booking_api.entities;

import com.github.sambhavmahajan.load_booking_api.dto.BookingDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookingEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    private LoadEntity load;
    public String transporterId;
    private double proposedRate;
    private String comment;
    private BookingStatus status = BookingStatus.PENDING;
    @CreationTimestamp
    private Timestamp createdAt;
}
