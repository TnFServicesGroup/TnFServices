package za.co.tut.booking.tnf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import za.co.tut.booking.tnf.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    
}
