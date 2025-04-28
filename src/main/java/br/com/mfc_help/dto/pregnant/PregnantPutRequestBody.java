package br.com.mfc_help.dto.pregnant;

import br.com.mfc_help.domain.Race;
import br.com.mfc_help.domain.pregnant.PregnantRisk;
import br.com.mfc_help.domain.pregnant.PregnantsBaby;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Set;

public record PregnantPutRequestBody(
        @NotBlank
        String id,
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
        @Min(value = 0, message = "The number of Vaginal Deliveries must be greater than or equal to 0")
        int vaginalDeliveries,
        @Min(value = 0, message = "The number of Cesarean Sections must be greater than or equal to 0")
        int cesareanSections,
        @Min(value = 0, message = "The number of Abortions must be greater than or equal to 0")
        int abortions,
        @Min(value = 0, message = "The number of Fetal Deaths must be greater than or equal to 0")
        int fetalDeaths,
        Race race,
        PregnantsBaby baby
) {
}
