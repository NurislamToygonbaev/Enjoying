package enjoying.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.DETACH;

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

    @OneToOne(cascade = {DETACH})
    private User user;

    @ManyToMany(cascade = {DETACH})
    private List<Announcement> announcements;

    @PrePersist
    private void prePersist(){
        this.announcements = new ArrayList<>();
        this.createdAt=LocalDate.now();
    }
}
