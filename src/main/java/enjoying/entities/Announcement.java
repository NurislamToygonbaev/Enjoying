package enjoying.entities;

import enjoying.enums.HouseType;
import enjoying.enums.Region;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "announcements")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcement_seq")
    @SequenceGenerator(name = "announcement_seq", allocationSize = 1)
    private Long id;

    @ElementCollection
    private List<String> images;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private HouseType houseType;
    private BigDecimal price;
    private int maxGuests;
    @Enumerated(EnumType.STRING)
    private Region region;
    private String town;
    private String address;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private boolean isActive;

    @OneToMany(mappedBy = "announcement")
    private List<RentInfo> rentInfos;

    @ManyToMany(mappedBy = "announcements")
    private List<Favorite> favorites;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "announcement")
    private List<FeedBack> feedBacks;

    @PrePersist
    private void prePersist(){
        this.createdAt=LocalDate.now();
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt=LocalDate.now();
    }
}
