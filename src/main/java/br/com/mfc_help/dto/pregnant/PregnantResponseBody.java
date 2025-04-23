package br.com.mfc_help.dto.pregnant;

import br.com.mfc_help.domain.pregnant.Pregnant;

import java.time.LocalDate;
import java.util.UUID;

public record PregnantResponseBody(
        UUID id,
        String name,
        LocalDate birthDate,
        String cns,
        LocalDate lastMenstruationDate,
        LocalDate firstUltrasoundDate,
        Integer gestationalAgeInWeeks,
        Integer gestationalAgeInDays,
        String jobPosition,
        Boolean plannedPregnancy,
        Boolean dentalAvaliation,
        Integer numberOfPreviousPregnancies,
        String race,
        Long healthUnityId,
        UUID doctorId
) {
    public PregnantResponseBody(Pregnant pregnant) {
        this(
                pregnant.getId(),
                pregnant.getName(),
                pregnant.getBirthDate(),
                pregnant.getCns(),
                pregnant.getLastMenstruationDate(),
                pregnant.getFirstUltrasoundDate(),
                pregnant.getGestationalAgeInWeeks(),
                pregnant.getGestationalAgeInDays(),
                pregnant.getJobPosition(),
                pregnant.getPlannedPregnancy(),
                pregnant.getDentalAvaliation(),
                pregnant.getNumberOfPreviousPregnancies(),
                pregnant.getRace().toString(),
                pregnant.getHealthUnity() != null ? pregnant.getHealthUnity().getCNES() : null,
                pregnant.getUser() != null ? pregnant.getUser().getId() : null
        );
    }
}
