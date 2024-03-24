package enjoying.repositories;

import enjoying.dto.response.DataAnnouncement;
import enjoying.entities.Announcement;
import enjoying.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    default Announcement getAnnouncementById(Long id){
        return findById(id).orElseThrow(() ->
                new NotFoundException("Announcement with id: "+id+" not found"));
    }



}
