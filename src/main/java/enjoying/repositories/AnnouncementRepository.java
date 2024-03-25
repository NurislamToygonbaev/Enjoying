package enjoying.repositories;

import enjoying.dto.response.AnnouncementResponses;
import enjoying.dto.response.MyAnnouncementResponses;
import enjoying.entities.Announcement;
import enjoying.enums.HouseType;
import enjoying.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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

    @Query("select a from Announcement a where a.id =:anId and a.isActive = true and a.isBlock = false ")
    Optional<Announcement> findByIdWHereIsActiveTrue(Long anId);

    default Announcement getAnnouncementByIdWhereIsActiveTrue(Long anId){
        return findByIdWHereIsActiveTrue(anId).orElseThrow(() ->
                new NotFoundException("Announcement with Id: "+anId+" not found"));
    }

    @Query("""
            select new enjoying.dto.response.MyAnnouncementResponses(
            a.images, a.id, a.price, a.rating, a.description, a.town,
             a.address, a.maxGuests, size(f.like.likes))
            from User u
            join u.announcements a
            join a.feedBacks f
            where a.isActive = true and a.isBlock = false
            and u.id =:userId
            """)
    List<MyAnnouncementResponses> myAnnouncements(Long userId);




}
