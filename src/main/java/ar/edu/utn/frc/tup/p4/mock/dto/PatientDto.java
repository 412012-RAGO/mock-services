package ar.edu.utn.frc.tup.p4.mock.dto;

import lombok.Data;

@Data
public class PatientDto {
    private Long patientId;
    private String patientName;
    private Integer dni;
}

