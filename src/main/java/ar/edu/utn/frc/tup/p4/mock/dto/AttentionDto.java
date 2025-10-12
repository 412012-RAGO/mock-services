package ar.edu.utn.frc.tup.p4.mock.dto;

import lombok.Data;
import java.util.List;

@Data
public class AttentionDto {
    private Long attentionId;
    private Long patientId;
    private Long insurerId;
    private Long planId;
    private Long branchId;
    private String protocolNumber;
    private String date;
    private List<PracticeDto> practices;
}

