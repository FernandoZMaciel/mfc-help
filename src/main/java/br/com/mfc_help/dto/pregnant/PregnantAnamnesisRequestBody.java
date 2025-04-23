package br.com.mfc_help.dto.pregnant;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record PregnantAnamnesisRequestBody(
        @NotNull(message = "The Pregnant ID cannot be blank")
        UUID pregnantId,
        @NotNull(message = "The comorbidities list cannot be null")
        List<Long> comorbiditiesList
) {

}
