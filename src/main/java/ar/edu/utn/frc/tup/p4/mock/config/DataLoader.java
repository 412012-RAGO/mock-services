package ar.edu.utn.frc.tup.p4.mock.config;

import ar.edu.utn.frc.tup.p4.mock.domain.*;
import ar.edu.utn.frc.tup.p4.mock.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private AttentionRepository attentionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private InsurerRepository insurerRepository;

    @Autowired
    private UserRepository userRepository;

    private final Random random = new Random();

    private static final String BRANCH_NAME = "MORRA INT";

    private record ProtocolInfo(String name, Integer ub) {}

    /** Protocolos con sus códigos y U.B. */
    private static final Map<Long, ProtocolInfo> PROTOCOLS = Map.ofEntries(
            Map.entry(1L, new ProtocolInfo("ACTO BIOQUÍMICO", 3)),
            Map.entry(192L, new ProtocolInfo("CREATININA - en sangre", 2)),
            Map.entry(412L, new ProtocolInfo("GLUCEMIA (C/U)", 1)),
            Map.entry(475L, new ProtocolInfo("HEMOGRAMA", 3)),
            Map.entry(546L, new ProtocolInfo("IONOGRAMA - sérico", 3)),
            Map.entry(711L, new ProtocolInfo("ORINA COMPLETA", 3)),
            Map.entry(865L, new ProtocolInfo("TIROTROFINA - TSH", 9)),
            Map.entry(867L, new ProtocolInfo("TIROXINA EFECTIVA - LIBRE (FT4 / T4L)", 9)),
            Map.entry(873L, new ProtocolInfo("TRANSAMINASA, GLUTÁMICO OXALACÉTICA (GOT / AST)", 1)),
            Map.entry(874L, new ProtocolInfo("TRANSAMINASA, GLUTÁMICO PIRÚVICA (GPT / AGT)", 1)),
            Map.entry(902L, new ProtocolInfo("UREA, sérica", 1))
    );

    @Override
    public void run(String... args) {
        attentionRepository.deleteAll();
        patientRepository.deleteAll();
        insurerRepository.deleteAll();
        branchRepository.deleteAll();
        userRepository.deleteAll();

        User user1 = new User();
        user1.setUserId(1L);
        user1.setUsername("tomas");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setUsername("john.doe");

        userRepository.saveAll(List.of(user1, user2));

        Branch branch = new Branch();
        branch.setBranchId(1L);
        branch.setBranchName(BRANCH_NAME);
        branchRepository.save(branch);

        Insurer osde = new Insurer(1L, "OSDE");
        Insurer daspu = new Insurer(2L, "DASPU");
        Insurer apross = new Insurer(3L, "APROSS");
        insurerRepository.saveAll(List.of(osde, daspu, apross));
        List<Insurer> insurers = List.of(osde, daspu, apross);

        List<Patient> patients = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            Patient p = new Patient();
            p.setPatientId((long) i);
            p.setPatientName("Patient " + i);
            p.setDni(30000000 + i);
            patientRepository.save(p);
            patients.add(p);
        }

        createAttentions(patients, insurers, 9, 2025);
        createAttentions(patients, insurers, 8, 2025);
        createAttentions(patients, insurers, 7, 2025);

        System.out.println("✅ Mock data loaded with attentions for different insurers and months.");
    }

    private void createAttentions(List<Patient> patients, List<Insurer> insurers, int month, int year) {
        AtomicLong attentionIdCounter = new AtomicLong(1);
        for (int i = 0; i < 10; i++) {
            Patient patient = patients.get(random.nextInt(patients.size()));
            Insurer insurer = insurers.get(random.nextInt(insurers.size()));

            Attention attention = new Attention();
            attention.setAttentionId(attentionIdCounter.getAndIncrement());
            attention.setPatientId(patient.getPatientId());
            attention.setInsurerId(insurer.getId());
            attention.setPlanId(1L); // Example plan ID
            attention.setBranchId(1L);
            attention.setProtocolNumber(generateProtocolNumber());
            attention.setDate(LocalDate.of(year, month, random.nextInt(28) + 1));

            attention.setPractices(createPracticesForAttention(insurer));

            attentionRepository.save(attention);
        }
    }

    private List<Practice> createPracticesForAttention(Insurer insurer) {
        List<Practice> practices = new ArrayList<>();
        List<Long> protocolNbus = new ArrayList<>(PROTOCOLS.keySet());
        int numberOfPractices = 1 + random.nextInt(5);

        // Use a Set to track used NBUs to avoid duplicates.
        java.util.Set<Long> usedNbus = new java.util.HashSet<>();

        // 1. Always add "ACTO BIOQUIMICO"
        Long actoBioquimicoNbu = 1L;
        ProtocolInfo actoBioquimicoInfo = PROTOCOLS.get(actoBioquimicoNbu);
        Practice actoBioquimicoPractice = new Practice();
        actoBioquimicoPractice.setNbu(actoBioquimicoNbu);
        actoBioquimicoPractice.setPracticeDescription(actoBioquimicoInfo.name());
        actoBioquimicoPractice.setCoverage(createCoverageForPractice(insurer, actoBioquimicoInfo));
        practices.add(actoBioquimicoPractice);
        usedNbus.add(actoBioquimicoNbu);

        // 2. Add other random practices, ensuring no duplicates.
        protocolNbus.remove(actoBioquimicoNbu); // Don't add it again
        int practicesToAdd = Math.min(numberOfPractices - 1, protocolNbus.size());

        for (int i = 0; i < practicesToAdd; i++) {
            Long nbu = protocolNbus.get(random.nextInt(protocolNbus.size()));
            if (usedNbus.contains(nbu)) {
                // Find a new one if we somehow get a duplicate (shouldn't happen with Set logic but good practice)
                i--; // retry
                continue;
            }
            ProtocolInfo protocolInfo = PROTOCOLS.get(nbu);

            Practice practice = new Practice();
            practice.setNbu(nbu);
            practice.setPracticeDescription(protocolInfo.name());
            practice.setCoverage(createCoverageForPractice(insurer, protocolInfo));
            practices.add(practice);
            usedNbus.add(nbu);
        }
        return practices;
    }

    private Coverage createCoverageForPractice(Insurer insurer, ProtocolInfo protocolInfo) {
        Coverage coverage = new Coverage();
        boolean isCovered = random.nextDouble() > 0.1; // 10% chance of not being covered

        BigDecimal ubValue;
        BigDecimal coveragePercent;

        switch (insurer.getName()) {
            case "OSDE":
                ubValue = new BigDecimal("150.50");
                coveragePercent = new BigDecimal("0.8");
                break;
            case "DASPU":
                ubValue = new BigDecimal("140.00");
                coveragePercent = new BigDecimal("0.9");
                break;
            case "APROSS":
                ubValue = new BigDecimal("130.75");
                coveragePercent = new BigDecimal("0.7");
                break;
            default:
                ubValue = new BigDecimal("100.00");
                coveragePercent = new BigDecimal("0.5");
                break;
        }

        if (isCovered) {
            coverage.setUb(protocolInfo.ub());
            coverage.setUbValue(ubValue);
            coverage.setCoverage(coveragePercent);
            if (random.nextBoolean()) {
                coverage.setCopayment(new BigDecimal(1000 + random.nextInt(1001)));
            } else {
                coverage.setCopayment(BigDecimal.ZERO);
            }
            coverage.setDescription(null);
        } else {
            coverage.setDescription("Particular");
            coverage.setUb(protocolInfo.ub() + random.nextInt(5)); // Different UB for particular
            coverage.setUbValue(ubValue.add(new BigDecimal(20))); // Different UB value
            coverage.setCoverage(BigDecimal.ZERO);
            coverage.setCopayment(BigDecimal.ZERO);
        }
        return coverage;
    }

    private String generateProtocolNumber() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
