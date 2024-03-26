package enjoying.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.DETACH;

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

    @ManyToOne(cascade = {DETACH})
    private Announcement announcement;

    @ManyToOne(cascade = {DETACH})
    private User user;

}
