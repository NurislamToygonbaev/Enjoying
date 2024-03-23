package enjoying.repositories;

import enjoying.entities.Announcement;
import enjoying.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    default Announcement getAnnouncementById(Long id){
        return findById(id).orElseThrow(() ->
                new NotFoundException("Announcement with id: "+id+" not found"));
    }
}
