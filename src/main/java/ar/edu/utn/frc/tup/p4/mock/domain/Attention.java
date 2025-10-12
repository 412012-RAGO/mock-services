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
    private Long insurerId;
    private Long planId;
    private Long branchId;
    private String protocolNumber;
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "attention_id")
    private List<Practice> practices;
}
