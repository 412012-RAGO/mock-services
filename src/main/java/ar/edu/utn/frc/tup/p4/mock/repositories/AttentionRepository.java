package ar.edu.utn.frc.tup.p4.mock.repositories;

import ar.edu.utn.frc.tup.p4.mock.domain.Attention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttentionRepository extends JpaRepository<Attention, Long> {
    List<Attention> findAllByInsurerId(Long insurerId);
    List<Attention> findAllByInsurerIdAndDateBetween(Long insurerId, LocalDate startDate, LocalDate endDate);
}
