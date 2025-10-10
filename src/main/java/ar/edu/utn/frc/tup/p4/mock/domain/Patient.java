package ar.edu.utn.frc.tup.p4.mock.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "patient")
@Data
public class Patient {
    @Id
    private Long patientId;
    private String patientName;
    private String lastName;
    private Integer dni;
    private LocalDate birthDate;
    private String gender;
    private String status;
    private Boolean isActive;

}
