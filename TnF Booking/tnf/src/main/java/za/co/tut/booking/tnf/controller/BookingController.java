package za.co.tut.booking.tnf.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import za.co.tut.booking.tnf.model.BookedItem;
import za.co.tut.booking.tnf.model.Booking;
import za.co.tut.booking.tnf.model.User;
import za.co.tut.booking.tnf.repository.BookingRepository;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;

    
    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();

        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Booking>> findBookingByEmail(@PathVariable String email) {
        List<Booking> bookings = bookingRepository.findBookingByUserEmail(email);
        
        if (!bookings.isEmpty()) {
            return ResponseEntity.ok(bookings);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
 
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable String status) {
        List<Booking> bookings = bookingRepository.findByStatus(status);
        if (bookings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId, HttpSession session) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElse(null);
        if (booking == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        session.setAttribute("booking", booking);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking newBooking,HttpSession session) {
        
         User user = (User) session.getAttribute("user");

         String status = "Booked";

        // Set the user to the address
        newBooking.setUser(user);
        newBooking.setStatus(status);

         // Retrieve the bookedItem from the session
        BookedItem bookedItem = (BookedItem) session.getAttribute("bookedItem");

        // Set the bookedItem to the newBooking
        newBooking.setBookedItem(bookedItem);

        ;
        
        Booking savedBooking = bookingRepository.save(newBooking);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long bookingId, @RequestBody Booking updatedBooking) {
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElse(null);
        if (existingBooking == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingBooking.setUser(updatedBooking.getUser());
        // Ignore updating 'createdAt' field
        Booking savedBooking = bookingRepository.save(existingBooking);
        return new ResponseEntity<>(savedBooking, HttpStatus.OK);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        bookingRepository.deleteById(bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{bookingId}/update-status")
    public ResponseEntity<Void> updateBookingStatusById(@PathVariable Long bookingId, @RequestParam String status) {
        try {
            Booking booking = bookingRepository.findById(bookingId).orElse(null);
            if (booking == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            booking.setStatus(status);
            bookingRepository.save(booking);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
