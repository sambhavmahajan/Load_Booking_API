package com.github.sambhavmahajan.load_booking_api.dto;

import com.github.sambhavmahajan.load_booking_api.entities.FacilityEntity;
import com.github.sambhavmahajan.load_booking_api.entities.LoadStatus;

public class LoadDTO {
    public String shipperId;
    public FacilityEntity facility;
    public String productType;
    public String truckType;
    public int noOfTrucks;
    public double weight;
    public String comment;
    public LoadStatus status;
}
