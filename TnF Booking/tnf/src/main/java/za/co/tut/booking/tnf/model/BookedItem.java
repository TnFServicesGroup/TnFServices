package za.co.tut.booking.tnf.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Booked_Item")
public class BookedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_item_id")
    private Long bookItemId;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private Item item;

    private int quantity;

    @Column(name = "hire_date")
    private String hireDate;

    @Column(name = "return_date")
    private String returnDate;
    
    @Column(name = "total_amount") // New column for total amount
    private double totalAmount;

    public BookedItem() {
    }

    public BookedItem(Item item, Booking booking, int quantity, String hireDate, String returnDate, double totalAmount) {
        this.item = item;
        this.quantity = quantity;;
        this.hireDate = hireDate;
        this.returnDate = returnDate;
        this.totalAmount = totalAmount; // Initialize totalAmount
    }

    // Getters and setters
    public Long getBookItemId() {
        return bookItemId;
    }

    public void setBookItemId(Long bookItemId) {
        this.bookItemId = bookItemId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "BookedItem{" +
                "bookItemId=" + bookItemId +
                ", item=" + item +
                ", quantity=" + quantity +
                ", hireDate=" + hireDate +
                ", returnDate=" + returnDate +
                ", totalAmount=" + totalAmount + // Include totalAmount in toString
                '}';
    }
}
