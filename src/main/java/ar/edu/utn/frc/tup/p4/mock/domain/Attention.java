package ar.edu.utn.frc.tup.p4.mock.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "attention")
@Data
public class Attention {
    @Id
    private Long attentionId;
    private Long patientId;
    private Integer protocolId;
    private String protocolName;
    private Long coverageId;
    private LocalDate attentionDate;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Practice> practices;
}
