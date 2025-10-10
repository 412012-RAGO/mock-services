package ar.edu.utn.frc.tup.p4.mock.controller;

import ar.edu.utn.frc.tup.p4.mock.domain.*;
import ar.edu.utn.frc.tup.p4.mock.dto.*;
import ar.edu.utn.frc.tup.p4.mock.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mock")
public class MockController {

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

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(value -> ResponseEntity.ok(modelMapper.map(value, UserDto.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<BranchDto> getBranch(@PathVariable Long branchId) {
        Optional<Branch> branch = branchRepository.findById(branchId);
        return branch.map(value -> ResponseEntity.ok(modelMapper.map(value, BranchDto.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/attention/{attentionId}")
    public ResponseEntity<AttentionDto> getAttention(@PathVariable Long attentionId) {
        Optional<Attention> attention = attentionRepository.findById(attentionId);
        if (attention.isPresent()) {
            AttentionDto attentionDto = modelMapper.map(attention.get(), AttentionDto.class);
            List<PracticeDto> practiceDtos = attention.get().getPractices().stream()
                    .map(practice -> modelMapper.map(practice, PracticeDto.class))
                    .collect(Collectors.toList());
            attentionDto.setPractices(practiceDtos);
            return ResponseEntity.ok(attentionDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable Long patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        return patient.map(value -> ResponseEntity.ok(modelMapper.map(value, PatientDto.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/coverage/{coverageId}")
    public ResponseEntity<CoverageDto> getCoverage(@PathVariable Long coverageId) {
        Optional<Coverage> coverage = coverageRepository.findById(coverageId);
        return coverage.map(value -> ResponseEntity.ok(modelMapper.map(value, CoverageDto.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/attentions")
    public ResponseEntity<List<AttentionDto>> getAllAttentions() {
        List<Attention> attentions = attentionRepository.findAll();

        List<AttentionDto> attentionDtos = attentions.stream()
                .map(att -> {
                    AttentionDto dto = modelMapper.map(att, AttentionDto.class);
                    if (att.getPractices() != null) {
                        dto.setPractices(
                                att.getPractices().stream()
                                        .map(p -> modelMapper.map(p, PracticeDto.class))
                                        .collect(Collectors.toList())
                        );
                    }
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(attentionDtos);
    }
}

