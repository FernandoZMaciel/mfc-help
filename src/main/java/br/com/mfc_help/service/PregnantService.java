package br.com.mfc_help.service;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import br.com.mfc_help.dto.pregnant.AdviceResponseBody;
import br.com.mfc_help.dto.pregnant.PregnantPutRequestBody;
import br.com.mfc_help.dto.pregnant.PregnantStatusResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mfc_help.domain.Race;
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

    public Pregnant findByIdOrThrowEntityNotFoundException(UUID id) {
        return pregnantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pregnant not found"));
    }

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
                pregnantPostRequestBody.vaginalDeliveries(),
                pregnantPostRequestBody.cesareanSections(),
                pregnantPostRequestBody.abortions(),
                pregnantPostRequestBody.fetalDeaths(),
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

    @Transactional
    public Pregnant updatePregnant(PregnantPutRequestBody pregnantPutRequestBody) {
        Pregnant savedPregnant = findByIdOrThrowEntityNotFoundException(UUID.fromString(pregnantPutRequestBody.id()));
        if (pregnantRepository.existsByCnsAndIdNot(pregnantPutRequestBody.cns(), savedPregnant.getId())) {
            throw new BadRequestException("An Pregnant with that CNS already exists");
        }


        Pregnant pregnant = new Pregnant(
                UUID.fromString(pregnantPutRequestBody.id()),
                pregnantPutRequestBody.name(),
                pregnantPutRequestBody.birthDate(),
                pregnantPutRequestBody.cns(),
                pregnantPutRequestBody.lastMenstruationDate(),
                pregnantPutRequestBody.firstUltrasoundDate(),
                pregnantPutRequestBody.gestationalAgeInWeeks(),
                pregnantPutRequestBody.gestationalAgeInDays(),
                pregnantPutRequestBody.jobPosition(),
                pregnantPutRequestBody.plannedPregnancy(),
                pregnantPutRequestBody.dentalAvaliation(),
                pregnantPutRequestBody.vaginalDeliveries(),
                pregnantPutRequestBody.cesareanSections(),
                pregnantPutRequestBody.abortions(),
                pregnantPutRequestBody.fetalDeaths(),
                pregnantPutRequestBody.race(),
                null,
                savedPregnant.getPregnantRisks(),
                healthUnityService.getHealthUnity(pregnantPutRequestBody.cnes()),
                userService.findByIdOrThrowBadRequestException(UUID.fromString(pregnantPutRequestBody.doctor()))
        );

        if (pregnantPutRequestBody.baby() != null){
            pregnant.setBaby(pregnantsBabyRepository.save(pregnantPutRequestBody.baby()));
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

    public PregnantStatusResponseBody gerPregnantStatus(Pregnant pregnant) {
        return new PregnantStatusResponseBody(
                getAge(pregnant),
                getGestationAge(pregnant),
                calculateTotalRisk(pregnant),
                getPredictedChildbirthDate(pregnant)
        );
    }

    private int calculateTotalRisk(Pregnant pregnant) {
        int risk = pregnant.getPregnantRisks().stream().mapToInt(PregnantRisk::getRisk).sum();

        long age = getAge(pregnant);

        if (age < 15 || age > 40) {
            risk += 2;
        }

        if (pregnant.getRace().equals(Race.NEGRO)) {
            risk += 1;
        }

        return risk;
    }

    private long getAge(Pregnant pregnant) {
        return ChronoUnit.YEARS.between(pregnant.getBirthDate(), LocalDate.now());
    }

    private String getGestationAge(Pregnant pregnant) {
        LocalDate conceptionDate = pregnant.getFirstUltrasoundDate()
                .minusDays(pregnant.getGestationalAgeInWeeks() * 7L)
                .minusDays(pregnant.getGestationalAgeInDays());
        LocalDate now = LocalDate.now();

        long totalGestationAgeInDays = ChronoUnit.DAYS.between(conceptionDate, now);
        int gestationAgeInWeeks = (int) (totalGestationAgeInDays / 7);
        int gestationAgeInDays = (int) (totalGestationAgeInDays % 7);

        return gestationAgeInWeeks + "+" + gestationAgeInDays;
    }

    private LocalDate getPredictedChildbirthDate(Pregnant pregnant) {
        return pregnant.getFirstUltrasoundDate()
                .plusDays(280)
                .minusDays(pregnant.getGestationalAgeInWeeks() * 7L)
                .minusDays(pregnant.getGestationalAgeInDays());
    }

    public List<AdviceResponseBody> getAdvices(Pregnant pregnant){
        long gestationAgeInWeeks = getGestationAgeInWeeks(pregnant);
        List<AdviceResponseBody> advices = new ArrayList<>();
        if (gestationAgeInWeeks < 12) {
            advices.add(new AdviceResponseBody("Ácido fólico até 12 semanas."));
            advices.add(new AdviceResponseBody("1º USGTV até 12 semanas."));
        }

        if (gestationAgeInWeeks > 8 && gestationAgeInWeeks < 15) {
            advices.add(new AdviceResponseBody("USG Morfológico 1º Tri até 14 semanas."));
        }

        if (gestationAgeInWeeks > 18 && gestationAgeInWeeks < 25) {
            advices.add(new AdviceResponseBody("USG Morfológico 2º Tri até 24 semanas."));
        }

        if (gestationAgeInWeeks > 18 && gestationAgeInWeeks < 31) {
            advices.add(new AdviceResponseBody("USG Obstétrico até 30 semanas."));
        }

        if (gestationAgeInWeeks > 12) {
            advices.add(new AdviceResponseBody("2 cps/dia carbonato de cálcio"));
        }

        return advices;
    }

    private long getGestationAgeInWeeks(Pregnant pregnant) {
        LocalDate conceptionDate = pregnant.getFirstUltrasoundDate()
                .minusDays(pregnant.getGestationalAgeInWeeks() * 7L)
                .minusDays(pregnant.getGestationalAgeInDays());
        LocalDate now = LocalDate.now();
        long totalGestationAgeInDays = ChronoUnit.DAYS.between(conceptionDate, now);

        return  (totalGestationAgeInDays / 7);
    }
}
