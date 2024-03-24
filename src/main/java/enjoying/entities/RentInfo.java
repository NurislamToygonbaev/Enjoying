package enjoying.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rent_infos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rent_seq")
    @SequenceGenerator(name = "rent_seq", allocationSize = 1)
    private Long id;
    LocalDate checkIn;
    LocalDate checkOut;

    @ManyToOne
    private Announcement announcement;

    @ManyToOne
    private User user;

}
