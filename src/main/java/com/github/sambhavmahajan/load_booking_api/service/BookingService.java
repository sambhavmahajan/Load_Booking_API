package com.github.sambhavmahajan.load_booking_api.service;

import com.github.sambhavmahajan.load_booking_api.dto.BookingDTO;
import com.github.sambhavmahajan.load_booking_api.entities.BookingEntity;
import com.github.sambhavmahajan.load_booking_api.entities.LoadEntity;
import com.github.sambhavmahajan.load_booking_api.entities.LoadStatus;
import com.github.sambhavmahajan.load_booking_api.entities.BookingStatus;
import com.github.sambhavmahajan.load_booking_api.repo.BookingRepo;
import com.github.sambhavmahajan.load_booking_api.repo.LoadRepo;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {
    private final BookingRepo bookingRepo;
    private final LoadRepo loadRepo;
    public BookingService(BookingRepo repo, LoadRepo loadRepo) {
        this.bookingRepo = repo;
        this.loadRepo = loadRepo;
    }
    public void transferFromDTO(BookingDTO dto, BookingEntity booking, LoadEntity load) {
        booking.setLoad(load);
        booking.setComment(dto.comment);
        booking.setTransporterId(dto.transporterId);
        booking.setProposedRate(dto.proposedRate);
    }
    public BookingEntity findByBookingId(UUID id) throws ResponseStatusException {
        Optional<BookingEntity> booking = bookingRepo.findById(id);
        if(booking.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Booking not found");
        }
        return booking.get();
    }
    public ArrayList<BookingEntity> findByShipperId(String id) {
        return bookingRepo.findByLoad_ShipperId(id);
    }
    public ArrayList<BookingEntity> findByTransporterId(String id) {
        return bookingRepo.findByTransporterId(id);
    }
    public ArrayList<BookingEntity> findByShipperIdAndTransporterId(String shipperId, String transporterId) {
        ArrayList<BookingEntity> res = bookingRepo.findByLoad_ShipperId(shipperId);
        res.retainAll(bookingRepo.findByTransporterId(transporterId));
        return res;
    }
    @Transactional
    public void createBooking(BookingDTO dtoBooking) throws ResponseStatusException {
        Optional<LoadEntity> oLoad = loadRepo.findById(dtoBooking.load);
        if (oLoad.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "No such existing load with given id");
        }

        LoadEntity load = oLoad.get();
        if (load.getStatus() == LoadStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Load is already cancelled");
        }

        load.setStatus(LoadStatus.BOOKED);
        loadRepo.save(load);
        BookingEntity booking = new BookingEntity();
        booking.setLoad(load);
        booking.setComment(dtoBooking.comment);
        booking.setTransporterId(dtoBooking.transporterId);
        booking.setProposedRate(dtoBooking.proposedRate);
        bookingRepo.save(booking);
    }
    @Transactional
    public void updateBooking(UUID id, BookingDTO bookingDTO) {
        Optional<LoadEntity> load = loadRepo.findById(id);
        Optional<BookingEntity> booking = bookingRepo.findById(id);
        if(booking.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Booking not found");
        }
        if(load.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Load not found");
        }
        if((load.get().getStatus() == LoadStatus.CANCELLED)) {
            transferFromDTO(bookingDTO, booking.get(), load.get());
            bookingRepo.save(booking.get());
        } else {
            transferFromDTO(bookingDTO, booking.get(), load.get());
            booking.get().setStatus(BookingStatus.ACCEPTED);
            bookingRepo.save(booking.get());
        }
    }
    @Transactional
    public void deleteBooking(UUID bookingId) {
        Optional<BookingEntity> oBooking = bookingRepo.findById(bookingId);
        if(oBooking.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Booking not found");
        }
        LoadEntity load = oBooking.get().getLoad();
        if(load == null) {
            bookingRepo.delete(oBooking.get());
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Load not found");
        }
        load.setStatus(LoadStatus.CANCELLED);
        loadRepo.save(load);
        bookingRepo.delete(oBooking.get());
    }
}
