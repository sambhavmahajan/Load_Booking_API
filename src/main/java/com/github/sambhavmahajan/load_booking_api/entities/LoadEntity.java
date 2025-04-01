package com.github.sambhavmahajan.load_booking_api.entities;

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
public class LoadEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String shipperId;
    private FacilityEntity facility;
    private String productType;
    private String truckType;
    private int noOfTrucks;
    private double weight;
    private String comment;
    @CreationTimestamp
    private Timestamp datePosted;
    private LoadStatus status = LoadStatus.POSTED;
}
