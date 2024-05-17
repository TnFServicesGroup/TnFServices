package za.co.tut.booking.tnf.repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import za.co.tut.booking.tnf.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserUserId(Long userId);
}