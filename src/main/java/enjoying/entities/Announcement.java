package enjoying.entities;

import enjoying.enums.HouseType;
import enjoying.enums.Region;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @SequenceGenerator(name = "post_seq", allocationSize = 1)
    private Long id;

    @ElementCollection
    private List<String> images;
    private String title;
    private String description;
    private HouseType houseType;
    private BigDecimal price;
    private int maxGuests;
    private Region region;
    private String town;
    private String address;
    @ElementCollection
    private List<Integer> rating;

    @OneToMany
    private List<Post> posts;

    @ManyToMany
    private List<Post> basketPost;

    @ManyToMany
    private List<Post> favoritePost;

}
