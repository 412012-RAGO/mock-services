package ar.edu.utn.frc.tup.p4.mock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "patient")
@Data
public class Patient {
    @Id
    private Long patientId;
    private String patientName;
    private Integer dni;
}
