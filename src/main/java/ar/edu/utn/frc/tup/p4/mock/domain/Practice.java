package ar.edu.utn.frc.tup.p4.mock.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "practice")
@Data
public class Practice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long practiceId;
    private Long nbu;
    private String practiceDescription;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coverage_id")
    private Coverage coverage;
}
