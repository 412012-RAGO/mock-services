package ar.edu.utn.frc.tup.p4.mock.config;

import ar.edu.utn.frc.tup.p4.mock.domain.*;
import ar.edu.utn.frc.tup.p4.mock.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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

    private final Random random = new Random();

    private static final String BRANCH_NAME = "MORRA INT";
    private static final double NBU = 684.24;

    /** Protocolos con sus códigos y U.B. */
    private static final Map<Integer, ProtocolInfo> PROTOCOLS = Map.ofEntries(
            Map.entry(1, new ProtocolInfo("ACTO BIOQUÍMICO", 3.00)),
            Map.entry(192, new ProtocolInfo("CREATININA - en sangre", 2.00)),
            Map.entry(412, new ProtocolInfo("GLUCEMIA (C/U)", 1.50)),
            Map.entry(475, new ProtocolInfo("HEMOGRAMA", 3.00)),
            Map.entry(546, new ProtocolInfo("IONOGRAMA - sérico", 3.50)),
            Map.entry(711, new ProtocolInfo("ORINA COMPLETA", 3.00)),
            Map.entry(865, new ProtocolInfo("TIROTROFINA - TSH", 9.00)),
            Map.entry(867, new ProtocolInfo("TIROXINA EFECTIVA - LIBRE (FT4 / T4L)", 9.00)),
            Map.entry(873, new ProtocolInfo("TRANSAMINASA, GLUTÁMICO OXALACÉTICA (GOT / AST)", 1.50)),
            Map.entry(874, new ProtocolInfo("TRANSAMINASA, GLUTÁMICO PIRÚVICA (GPT / AGT)", 1.50)),
            Map.entry(902, new ProtocolInfo("UREA, sérica", 1.50))
    );

    @Override
    public void run(String... args) {
        attentionRepository.deleteAll();
        patientRepository.deleteAll();
        coverageRepository.deleteAll();
        branchRepository.deleteAll();

        Branch branch = new Branch();
        branch.setBranchId(1L);
        branch.setBranchName(BRANCH_NAME);
        branchRepository.save(branch);

        Coverage daspu = new Coverage();
        daspu.setCoverageId(1L);
        daspu.setCoverageName("DASPU");
        coverageRepository.save(daspu);

        Coverage osde = new Coverage();
        osde.setCoverageId(2L);
        osde.setCoverageName("OSDE");
        coverageRepository.save(osde);

        List<Patient> daspuPatients = new ArrayList<>();
        List<Patient> osdePatients = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Patient p = new Patient();
            p.setPatientId((long) i);
            p.setPatientName("Paciente DASPU " + i);
            p.setDni(30000000 + i);
            patientRepository.save(p);
            daspuPatients.add(p);
        }

        for (int i = 6; i <= 10; i++) {
            Patient p = new Patient();
            p.setPatientId((long) i);
            p.setPatientName("Paciente OSDE " + (i - 5));
            p.setDni(40000000 + i);
            patientRepository.save(p);
            osdePatients.add(p);
        }

        createAttentions(daspuPatients, daspu, 9, 2025);
        createAttentions(osdePatients, osde, 8, 2025);

        System.out.println("✅ Mock cargado con DASPU (septiembre) y OSDE (agosto)");
    }

    private void createAttentions(List<Patient> patients, Coverage coverage, int month, int year) {
        List<Integer> protocolIds = new ArrayList<>(PROTOCOLS.keySet());

        for (int i = 0; i < 10; i++) {
            Patient patient = patients.get(random.nextInt(patients.size()));
            int code = protocolIds.get(random.nextInt(protocolIds.size()));
            ProtocolInfo info = PROTOCOLS.get(code);

            Attention attention = new Attention();
            attention.setAttentionId((long) (1000 + i));
            attention.setPatientId(patient.getPatientId());
            attention.setCoverageId(coverage.getCoverageId());

            attention.setProtocolId(code);
            attention.setProtocolName(info.name);

            Practice practice = new Practice();
            practice.setPracticeId((long) (i + 1));
            practice.setPracticeDescription(info.name);
            practice.setCoverageType(coverage.getCoverageName());
            practice.setQuantity(1);
            practice.setUnitPrice(info.ub * NBU); // UB × NBU = Importe
            practice.setCoverage(practice.getUnitPrice() * (coverage.getCoverageName().equals("DASPU") ? 0.8 : 0.9)); // porcentaje
            practice.setCoinsurance(practice.getUnitPrice() - practice.getCoverage());

            attention.setPractices(List.of(practice));

            LocalDateTime fecha = LocalDateTime.of(year, month, random.nextInt(28) + 1, 10, 0);
            attention.setProtocolName(info.name + " (" + fecha.toLocalDate() + ")");

            attentionRepository.save(attention);
        }
    }

    /** Mini clase auxiliar para guardar nombre + UB */
    private record ProtocolInfo(String name, Double ub) {}
}
