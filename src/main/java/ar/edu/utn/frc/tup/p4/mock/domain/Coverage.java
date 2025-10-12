package ar.edu.utn.frc.tup.p4.mock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "coverage")
@Data
public class Coverage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coverageId;
    private BigDecimal ubValue;
    private BigDecimal coverage;
    private BigDecimal copayment;
    private Integer ub;
    private String description;
}
