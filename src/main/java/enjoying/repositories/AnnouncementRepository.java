package enjoying.repositories;

import enjoying.dto.response.DataAnnouncement;
import enjoying.entities.Announcement;
import enjoying.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    default Announcement getAnnouncementById(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundException("Announcement with id: " + id + " not found"));
    }

    @Query("select a from Announcement a where a.id =:anId and a.isActive = false")
    Optional<Announcement> findByIdWHereIsActive(Long anId);

    default Announcement getAnnouncementByIdWhereIsActive(Long anId){
       return findByIdWHereIsActive(anId).orElseThrow(() ->
               new NotFoundException("Announcement with Id: "+anId+" not found"));
    }



}
