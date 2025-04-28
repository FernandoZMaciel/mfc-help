package br.com.mfc_help.dto.pregnant;

import java.time.LocalDate;

public record PregnantStatusResponseBody(
        long age,
        String gestationalAge,
        Integer risk,
        LocalDate predictedChildbirthDate
) {
}
