package ar.edu.utn.frc.tup.p4.mock.dto;

import lombok.Data;

@Data
public class PracticeDto {
    private Long practiceId;
    private String coverageType;
    private String practiceDescription;
    private Integer quantity;
    private Double unitPrice;
    private Double coverage;
    private Double coinsurance;
}

