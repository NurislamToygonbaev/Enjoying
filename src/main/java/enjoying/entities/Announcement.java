package enjoying.entities;

import enjoying.enums.HouseType;
import enjoying.enums.Region;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

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
    private double rating;
    private String reject;
    private boolean isBlock;

    @OneToMany(mappedBy = "announcement", cascade = {REMOVE})
    private List<RentInfo> rentInfos;

    @ManyToMany(mappedBy = "announcements", cascade = {REMOVE})
    private List<Favorite> favorites;

    @ManyToOne(cascade = {DETACH})
    private User user;

    @OneToMany(mappedBy = "announcement", cascade = {REMOVE})
    private List<FeedBack> feedBacks;

    @PrePersist
    private void prePersist(){
        this.createdAt = LocalDate.now();
        this.rentInfos = new ArrayList<>();
        this.favorites = new ArrayList<>();
        this.feedBacks = new ArrayList<>();
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt=LocalDate.now();
    }
}
