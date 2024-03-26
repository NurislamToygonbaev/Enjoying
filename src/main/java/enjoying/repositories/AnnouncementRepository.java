package enjoying.repositories;

import enjoying.entities.Announcement;
import enjoying.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    default Announcement getAnnouncementById(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundException("Announcement with id: " + id + " not found"));
    }

    @Query("select a from Announcement a where a.id =:anId")
    Optional<Announcement> findByIdWHereIsActive(Long anId);

    default Announcement getAnnouncementByIdWhereIsActive(Long anId){
       return findByIdWHereIsActive(anId).orElseThrow(() ->
               new NotFoundException("Announcement with Id: "+anId+" not found"));
    }

    @Query("select a from Announcement a where a.id =:anId and a.isActive = true and a.isBlock = false ")
    Optional<Announcement> findByIdWHereIsActiveTrue(Long anId);
    default Announcement getAnnouncementByIdWhereIsActiveTrue(Long anId){
        return findByIdWHereIsActiveTrue(anId).orElseThrow(() ->
                new NotFoundException("Announcement with Id: "+anId+" not found"));
    }
    @Query("select a from Announcement a where a.id =:anId and a.isBlock = false ")
    Optional<Announcement> findByIdWHere(Long anId);

    default Announcement findByIdWHereIsBlockFalse(Long anId){
        return findByIdWHere(anId).orElseThrow(() ->
                new NotFoundException("Announcement with Id: "+anId+" not found"));
    }



}
