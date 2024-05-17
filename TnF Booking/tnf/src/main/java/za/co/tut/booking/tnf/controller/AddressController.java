package za.co.tut.booking.tnf.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import za.co.tut.booking.tnf.model.Address;
import za.co.tut.booking.tnf.model.User;
import za.co.tut.booking.tnf.repository.AddressRepository;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @PostMapping
    public ResponseEntity<Address> addAddress(@RequestBody Address address, HttpSession session) {
        // Retrieve the user from the session
        User user = (User) session.getAttribute("user");

        // Set the user to the address
        address.setUser(user);

        // Save the address with the associated user
        Address savedAddress = addressRepository.save(address);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    @GetMapping("/user-address/{userId}")
    public ResponseEntity<List<Address>> getAddressByUserId(@PathVariable Long userId, HttpSession session) {
        List<Address> addresses = addressRepository.findByUserUserId(userId);

        if (!addresses.isEmpty()) {
            session.setAttribute("userAddresses", addresses);
            return ResponseEntity.ok(addresses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address updatedAddress, HttpSession session) {
       
        // Retrieve the existing address entity from the database
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    
        Address existingAddress = optionalAddress.get();
    
        // Update the properties of the existing address entity with the values from the updatedAddress object
        existingAddress.setHouseNumber(updatedAddress.getHouseNumber());
        existingAddress.setStreetName(updatedAddress.getStreetName());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setPostalCode(updatedAddress.getPostalCode());
        existingAddress.setProvince(updatedAddress.getProvince());
    
    
        // Save the updated address entity back to the database
        Address savedAddress = addressRepository.save(existingAddress);
    
        return new ResponseEntity<>(savedAddress, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        return addressRepository.findById(id)
                .map(address -> new ResponseEntity<>(address, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

 

}
