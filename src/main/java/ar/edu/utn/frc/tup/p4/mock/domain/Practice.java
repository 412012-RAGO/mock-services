package ar.edu.utn.frc.tup.p4.mock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "practice")
@Data
public class Practice {
    @Id
    private Long practiceId;
    private String coverageType;
    private String practiceDescription;
    private Integer quantity;
    private Double unitPrice;
    private Double coverage;
    private Double coinsurance;
}
