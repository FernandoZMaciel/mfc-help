package br.com.mfc_help.service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mfc_help.domain.pregnant.Pregnant;
import br.com.mfc_help.domain.pregnant.PregnantRisk;
import br.com.mfc_help.dto.pregnant.PregnantAnamnesisRequestBody;
import br.com.mfc_help.dto.pregnant.PregnantPostRequestBody;
import br.com.mfc_help.infra.exception.BadRequestException;
import br.com.mfc_help.repository.PregnantRepository;
import br.com.mfc_help.repository.PregnantRiskRepository;
import br.com.mfc_help.repository.PregnantsBabyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PregnantService {

    @Autowired
    private PregnantRepository pregnantRepository;

    @Autowired
    private PregnantsBabyRepository pregnantsBabyRepository;

    @Autowired
    private PregnantRiskRepository pregnantRiskRepository;

    @Autowired
    private HealthUnityService healthUnityService;

    @Autowired
    private UserService userService;

    @Transactional
    public Pregnant savePregnant(PregnantPostRequestBody pregnantPostRequestBody) {

        if (pregnantRepository.existsByCns(pregnantPostRequestBody.cns())) {
            throw new BadRequestException("An Pregnant with that CNS already exists");
        }

        Pregnant pregnant = new Pregnant(
                null,
                pregnantPostRequestBody.name(),
                pregnantPostRequestBody.birthDate(),
                pregnantPostRequestBody.cns(),
                pregnantPostRequestBody.lastMenstruationDate(),
                pregnantPostRequestBody.firstUltrasoundDate(),
                pregnantPostRequestBody.gestationalAgeInWeeks(),
                pregnantPostRequestBody.gestationalAgeInDays(),
                pregnantPostRequestBody.jobPosition(),
                pregnantPostRequestBody.plannedPregnancy(),
                pregnantPostRequestBody.dentalAvaliation(),
                pregnantPostRequestBody.numberOfPreviousPregnancies(),
                pregnantPostRequestBody.race(),
                null,
                null,
                healthUnityService.getHealthUnity(pregnantPostRequestBody.cnes()),
                userService.findByIdOrThrowBadRequestException(UUID.fromString(pregnantPostRequestBody.doctor()))
                );

        if (pregnantPostRequestBody.baby() != null){
            pregnant.setBaby(pregnantsBabyRepository.save(pregnantPostRequestBody.baby()));
        }

        return pregnantRepository.save(pregnant);
    }
    
    public Pregnant doAnamnesis(PregnantAnamnesisRequestBody pregnantAnamnesisRequestBody) {
        Pregnant pregnant = pregnantRepository.findById(pregnantAnamnesisRequestBody.pregnantId()).orElseThrow(() -> new EntityNotFoundException("Pregnant not found"));
        Set<PregnantRisk> newRisks = new HashSet<>();

        for (Long comorbidityId : pregnantAnamnesisRequestBody.comorbiditiesList()) {
            Optional<PregnantRisk> risk = pregnantRiskRepository.findById(comorbidityId);
            risk.ifPresent(newRisks::add);
        }

        pregnant.setPregnantRisks(newRisks);
        return pregnantRepository.save(pregnant);
    }
}
