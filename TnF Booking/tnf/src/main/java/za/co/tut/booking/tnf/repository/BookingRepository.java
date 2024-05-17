package za.co.tut.booking.tnf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import za.co.tut.booking.tnf.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{
    
    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.status = :status WHERE b.bookingId = :bookingId")
    void updateBookingStatusById(Long bookingId, String status);
    List<Booking> findByStatus(String status);
    List<Booking> findBookingByUserEmail(String email);
}
  

