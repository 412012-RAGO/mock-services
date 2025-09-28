package ar.edu.utn.frc.tup.p4.mock.repositories;

import ar.edu.utn.frc.tup.p4.mock.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}

