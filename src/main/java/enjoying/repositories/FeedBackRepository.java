package enjoying.repositories;

import enjoying.entities.FeedBack;
import enjoying.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
    default FeedBack getFeedBackById(Long feedId){
        return findById(feedId).orElseThrow(() ->
                new NotFoundException("FeedBack with id: "+feedId+" not found"));
    }
}
