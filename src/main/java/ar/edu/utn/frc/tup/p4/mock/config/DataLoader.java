package ar.edu.utn.frc.tup.p4.mock.config;

import ar.edu.utn.frc.tup.p4.mock.domain.*;
import ar.edu.utn.frc.tup.p4.mock.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private AttentionRepository attentionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CoverageRepository coverageRepository;

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();

        // Create Users
        for (long i = 1; i <= 10; i++) {
            User user = new User();
            user.setUserId(i);
            user.setUsername("user" + i);
            userRepository.save(user);
        }

        // Create Branches
        for (long i = 1; i <= 10; i++) {
            Branch branch = new Branch();
            branch.setBranchId(i);
            branch.setBranchName("Branch " + i);
            branchRepository.save(branch);
        }

        // Create Patients
        for (long i = 1; i <= 10; i++) {
            Patient patient = new Patient();
            patient.setPatientId(i);
            patient.setPatientName("Patient " + i);
            patient.setDni(random.nextInt(100000000));
            patientRepository.save(patient);
        }

        // Create Coverages
        for (long i = 1; i <= 10; i++) {
            Coverage coverage = new Coverage();
            coverage.setCoverageId(i);
            coverage.setCoverageName("Coverage " + i);
            coverageRepository.save(coverage);
        }

        // Create Attentions
        for (long i = 1; i <= 10; i++) {
            Attention attention = new Attention();
            attention.setAttentionId(i);
            attention.setPatientId((long) (random.nextInt(10) + 1));
            attention.setProtocol("Protocol " + i);
            attention.setCoverageId((long) (random.nextInt(10) + 1));

            List<Practice> practices = new ArrayList<>();
            for (long j = 1; j <= 3; j++) {
                Practice practice = new Practice();
                practice.setPracticeId((i - 1) * 3 + j);
                practice.setCoverageType("Type " + j);
                practice.setPracticeDescription("Description " + j);
                practice.setQuantity(random.nextInt(5) + 1);
                practice.setUnitPrice(random.nextDouble() * 100);
                practice.setCoverage(random.nextDouble() * 50);
                practice.setCoinsurance(random.nextDouble() * 10);
                practices.add(practice);
            }
            attention.setPractices(practices);
            attentionRepository.save(attention);
        }
    }
}

