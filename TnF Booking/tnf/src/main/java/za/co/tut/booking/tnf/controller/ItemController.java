package za.co.tut.booking.tnf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import za.co.tut.booking.tnf.model.Item;
import za.co.tut.booking.tnf.repository.ItemRepository;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemRepository.findAll();
        // Encode image bytes to Base64 strings for each item
        items.forEach(item -> {
            if (item.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(item.getImage());
                item.setImageBase64(base64Image);
            }
        });
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            // Store the item details in the session
            session.setAttribute("item_price", item.getPrice());
            session.setAttribute("item_name", item.getName());
            session.setAttribute("item", item);

            // Return the item details
            String base64Image = Base64.getEncoder().encodeToString(item.getImage());
            item.setImageBase64(base64Image);
            
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestParam("file") MultipartFile file,
                                           @RequestParam("name") String name,
                                           @RequestParam("description") String description,
                                           @RequestParam("quantity") int quantity,
                                           @RequestParam("price") double price) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            byte[] imageBytes = file.getBytes();

            Item item = new Item();
            item.setImage(imageBytes);
            item.setName(name);
            item.setDescription(description);
            item.setQuantity(quantity);
            item.setPrice(price);

            Item newItem = itemRepository.save(item);

            return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("quantity") int quantity, @RequestParam("price") double price) {
        try {
            Optional<Item> optionalItem = itemRepository.findById(id);
            if (!optionalItem.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            Item item = optionalItem.get();
            
            if (!file.isEmpty()) {
                byte[] imageBytes = file.getBytes();
                item.setImage(imageBytes);
            }

            item.setName(name);
            item.setDescription(description);
            item.setQuantity(quantity);
            item.setPrice(price);

            Item updatedItem = itemRepository.save(item);

            return ResponseEntity.ok(updatedItem);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (!itemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

