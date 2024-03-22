package enjoying.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Table(name = "likes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_seq")
    @SequenceGenerator(name = "like_seq", allocationSize = 1)
    private Long id;
    @ElementCollection
    private List<Long> likes;
    @ElementCollection
    private List<Long> disLikes;

    @OneToOne
    private FeedBack feedBack;
}
