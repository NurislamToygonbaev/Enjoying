package enjoying.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "favorites")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fav_seq")
    @SequenceGenerator(name = "fav_seq", allocationSize = 1)
    private Long id;
    private LocalDate createdAt;

    @OneToOne
    private User user;

    @ManyToMany
    private List<Announcement> announcements;

    @PrePersist
    private void prePersist(){
        this.createdAt=LocalDate.now();
    }
}
