package enjoying.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "booking")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @SequenceGenerator(name = "post_seq", allocationSize = 1)
    private Long id;}
