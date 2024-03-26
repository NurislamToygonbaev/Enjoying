package enjoying.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.REMOVE;

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

    @ManyToOne(cascade = {DETACH})
    private User user;

    @OneToOne(mappedBy = "feedBack", cascade = {REMOVE})
    private Like like;

    @PrePersist
    private void createdAd(){
        this.createdAt = LocalDate.now();
    }
}
