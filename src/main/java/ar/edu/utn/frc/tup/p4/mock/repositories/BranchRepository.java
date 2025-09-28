package ar.edu.utn.frc.tup.p4.mock.repositories;

import ar.edu.utn.frc.tup.p4.mock.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
}

