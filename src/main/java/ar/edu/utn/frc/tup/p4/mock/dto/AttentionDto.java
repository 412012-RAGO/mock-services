package ar.edu.utn.frc.tup.p4.mock.dto;

import lombok.Data;
import java.util.List;

@Data
public class AttentionDto {
    private Long attentionId;
    private Long patientId;
    private Long coverageId;
    private Integer protocolId;
    private String protocolName;
    private List<PracticeDto> practices;
}

