package ar.edu.utn.frc.tup.p4.mock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "insurer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Insurer {
    @Id
    Long id;
    String name;
}
