package br.com.mfc_help.dto.pregnant;

import br.com.mfc_help.domain.Race;
import br.com.mfc_help.domain.pregnant.PregnantsBaby;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PregnantPostRequestBody(
        @NotBlank(message = "The Name cannot be Blank")
        String name,
        @NotNull(message = "The Birth Date cannot be null")
        @Past(message = "The Birth Date must be in the past")
        LocalDate birthDate,
        @NotBlank(message = "The CNS cannot be Blank")
        @Size(min = 15, max = 15, message = "The CNS must have exactly 15 characters")
        String cns,
        @NotNull(message = "The Last Menstruation Date cannot be null")
        LocalDate lastMenstruationDate,
        @NotNull(message = "The CNES cannot be blank")
        Long cnes,
        @NotBlank(message = "The Doctor cannot be blank")
        String doctor,
        LocalDate firstUltrasoundDate,
        @Min(value = 0, message = "The Gestational Age in Weeks must be greater than or equal to 0")
        @Max(value = 50, message = "The Gestational Age in Weeks must be less than or equal to 50")
        Integer gestationalAgeInWeeks,
        @Min(value = 0, message = "The Gestational Age in Days must be greater than or equal to 0")
        @Max(value = 6, message = "The Gestational Age in Days must be less than or equal to 6")
        Integer gestationalAgeInDays,
        String jobPosition,
        Boolean plannedPregnancy,
        Boolean dentalAvaliation,
        int numberOfPreviousPregnancies,
        Race race,
        PregnantsBaby baby
) {
}
