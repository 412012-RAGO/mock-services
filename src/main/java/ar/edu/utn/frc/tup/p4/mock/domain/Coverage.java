package ar.edu.utn.frc.tup.p4.mock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "coverage")
@Data
public class Coverage {
    @Id
    private Long coverageId;
    private String coverageName;
}
