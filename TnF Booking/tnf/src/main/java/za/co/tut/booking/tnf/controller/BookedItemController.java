package za.co.tut.booking.tnf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import za.co.tut.booking.tnf.model.BookedItem;
import za.co.tut.booking.tnf.model.Item;
import za.co.tut.booking.tnf.repository.BookedItemRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/booked-items")
public class BookedItemController {

    @Autowired
    private BookedItemRepository bookedItemRepository;

    @GetMapping
    public ResponseEntity<List<BookedItem>> getAllBookedItems() {
        List<BookedItem> bookedItems = bookedItemRepository.findAll();
        return ResponseEntity.ok(bookedItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookedItem> getBookedItemById(@PathVariable("id") Long id) {
        Optional<BookedItem> optionalBookedItem = bookedItemRepository.findById(id);
        return optionalBookedItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookedItem> addBookedItem(@RequestBody BookedItem bookedItem, HttpSession session) {
        // Retrieve item and booking from session attributes
        Item item = (Item) session.getAttribute("item");
    
        // Set item and booking in bookedItem object
        bookedItem.setItem(item);
    
        // Save bookedItem to the repository
        bookedItemRepository.save(bookedItem);
    
        session.setAttribute("bookedItem", bookedItem);
    
        return ResponseEntity.status(HttpStatus.CREATED).body(bookedItem);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<BookedItem> updateBookedItem(@PathVariable("id") Long id, @RequestBody BookedItem updatedBookedItem) {
        Optional<BookedItem> optionalBookedItem = bookedItemRepository.findById(id);
        if (optionalBookedItem.isPresent()) {
            updatedBookedItem.setBookItemId(id);
            bookedItemRepository.save(updatedBookedItem);
            return ResponseEntity.ok(updatedBookedItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookedItem(@PathVariable("id") Long id) {
        Optional<BookedItem> optionalBookedItem = bookedItemRepository.findById(id);
        if (optionalBookedItem.isPresent()) {
            bookedItemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
