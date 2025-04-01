package com.github.sambhavmahajan.load_booking_api.dto;

import com.github.sambhavmahajan.load_booking_api.entities.BookingStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class BookingDTO {
    public UUID load;
    public String transporterId;
    public double proposedRate;
    public String comment;
    public BookingStatus status;
}
