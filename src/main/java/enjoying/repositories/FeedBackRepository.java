package enjoying.repositories;

import enjoying.entities.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
}
