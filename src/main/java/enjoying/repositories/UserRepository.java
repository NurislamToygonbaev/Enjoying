package enjoying.repositories;

import enjoying.entities.User;
import enjoying.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);


   default User getByEmail(String email){
       return findByEmail(email).orElseThrow(() ->
               new NotFoundException("User with email not found"));
   }
}