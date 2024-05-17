package za.co.tut.booking.tnf.model;

import java.util.Arrays;

import jakarta.persistence.*;

@Entity
@Table(name = "Item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Lob
    private byte[] image;

    private String name;
    private String description;
    private int quantity;
    private double price;


    @Transient // This annotation is used to indicate that this field should not be persisted in the database
    private String imageBase64;


    public Item() {
    }

    public Item(byte[] image, String name, String description,  int quantity, double price) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    @Override
    public String toString() {
        return "Item [itemId=" + itemId + ", image=" + Arrays.toString(image) + ", name=" + name + ", description="
                + description + ", quantity=" + quantity + ", price=" + price + "]";
    }

    
   

    

    
}

