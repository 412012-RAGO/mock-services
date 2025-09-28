package ar.edu.utn.frc.tup.p4.mock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "branch")
@Data
public class Branch {
    @Id
    private Long branchId;
    private String branchName;
}
