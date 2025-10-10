package ar.edu.utn.frc.tup.p4.mock.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDto {
    private Long patientId;
    private String patientName;

    /** 🆕 Nuevos campos */
    private String lastName;
    private Integer dni;
    private LocalDate birthDate;
    private String gender;
    private String status;
    private Boolean isActive;
}

