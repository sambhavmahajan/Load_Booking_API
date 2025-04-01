package com.github.sambhavmahajan.load_booking_api.controller;

import com.github.sambhavmahajan.load_booking_api.dto.BookingDTO;
import com.github.sambhavmahajan.load_booking_api.entities.BookingEntity;
import com.github.sambhavmahajan.load_booking_api.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping
    public void createBooking(@RequestBody BookingDTO bookingDTO) {
        bookingService.createBooking(bookingDTO);
    }
    @GetMapping
    public ArrayList<BookingEntity> getBookings(@RequestParam String shipperId, @RequestParam String transporterId) {
        return bookingService.findByShipperIdAndTransporterId(shipperId, transporterId);
    }
    @GetMapping("/{bookingId}")
    public BookingEntity getBooking(@PathVariable("bookingId") UUID bookingId) {
        return bookingService.findByBookingId(bookingId);
    }
    @PutMapping("/{bookingId}")
    public void updateBooking(UUID bookingId, BookingDTO bookingDTO) {
        bookingService.updateBooking(bookingId ,bookingDTO);
    }
}
