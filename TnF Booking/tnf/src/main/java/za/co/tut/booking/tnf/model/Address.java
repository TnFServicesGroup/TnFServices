package za.co.tut.booking.tnf.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "street_name")
    private String streetName;

    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    private String province;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public Address() {
    }

  

    public Address(Long addressId, String houseNumber, String streetName, String city, String postalCode,
            String province, User user) {
        this.addressId = addressId;
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
        this.user = user;
    }



    // Getters and setters
    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }



    public User getUser() {
        return user;
    }



    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Address [addressId=" + addressId + ", houseNumber=" + houseNumber + ", streetName=" + streetName
                + ", city=" + city + ", postalCode=" + postalCode + ", province=" + province + ", user=" + user + "]";
    }
 
}