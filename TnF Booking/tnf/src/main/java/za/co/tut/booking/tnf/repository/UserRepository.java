package za.co.tut.booking.tnf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import za.co.tut.booking.tnf.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
 
}
