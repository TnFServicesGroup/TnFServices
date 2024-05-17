package za.co.tut.booking.tnf.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @ManyToOne
    @JoinColumn(name = "book_item_id", referencedColumnName = "book_item_id")
    private BookedItem bookedItem;

    @Column(name = "status")
    private String status;

   
    public Booking() {
    }

   

    public Booking(User user, LocalDateTime createdAt, BookedItem bookedItem, String status) {
        this.user = user;
        this.createdAt = createdAt;
        this.bookedItem = bookedItem;
        this.status = status;
    }



    // Getters and setters for all attributes
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BookedItem getBookedItem() {
        return bookedItem;
    }

    public void setBookedItem(BookedItem bookedItem) {
        this.bookedItem = bookedItem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", user=" + user +
                ", createdAt=" + createdAt +
                ", bookedItem=" + bookedItem +
                ", status='" + status + '\'' +
                '}';
    }

    
}