package enjoying.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "feedbacks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feed_seq")
    @SequenceGenerator(name = "feed_seq", allocationSize = 1)
    private Long id;
    private Integer rating;
    @ElementCollection
    private List<String> images;
    private String description;
    private LocalDate createdAt;

    @ManyToOne
    private Announcement announcement;

    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "feedBack")
    private Like like;

    @PrePersist
    private void createdAd(){
        this.createdAt = LocalDate.now();
    }
}
