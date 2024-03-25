package enjoying.repositories;

import enjoying.dto.response.PopularResponse;
import enjoying.entities.Announcement;
import enjoying.entities.Like;
import enjoying.enums.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("select a from Announcement  a order by a.rating desc limit 7")
    List<Announcement> popularSeven();
@Query("select a from Announcement  a where a.region = :region")
    List<Announcement> regionAnnouncement(Region region);
}
