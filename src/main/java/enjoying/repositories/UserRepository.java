package enjoying.repositories;

import enjoying.entities.User;
import enjoying.enums.Role;
import enjoying.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);


   default User getByEmail(String email){
       return findByEmail(email).orElseThrow(() ->
               new NotFoundException("User with: "+email+" not found"));
   }

   Optional<User> findById(Long id);
    default User getById(Long userId){
        return findById(userId).orElseThrow(() ->
                new NotFoundException("User with: "+userId+" not found"));
    }


    boolean existsByEmail(String email);

    User findByRole(Role role);
}