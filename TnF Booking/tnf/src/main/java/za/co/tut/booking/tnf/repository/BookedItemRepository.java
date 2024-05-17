package za.co.tut.booking.tnf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import za.co.tut.booking.tnf.model.BookedItem;

public interface BookedItemRepository extends JpaRepository<BookedItem,Long>{
    

}
