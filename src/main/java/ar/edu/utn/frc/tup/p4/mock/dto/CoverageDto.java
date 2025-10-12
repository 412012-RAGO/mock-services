package ar.edu.utn.frc.tup.p4.mock.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CoverageDto {
    private Long coverageId;
    private BigDecimal ubValue;
    private BigDecimal coverage;
    private BigDecimal copayment;
    private Integer ub;
    private String description;
}

