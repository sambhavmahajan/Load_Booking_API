package com.github.sambhavmahajan.load_booking_api.entities;


import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class FacilityEntity {
    private Long facilityId;
    private String loadingPoint;
    private String unloadingPoint;
    private Timestamp loadingDate;
    private Timestamp unloadingDate;
}
