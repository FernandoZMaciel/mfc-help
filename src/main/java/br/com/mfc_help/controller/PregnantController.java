package br.com.mfc_help.controller;

import br.com.mfc_help.domain.pregnant.Pregnant;
import br.com.mfc_help.dto.pregnant.PregnantAnamnesisRequestBody;
import br.com.mfc_help.dto.pregnant.PregnantPostRequestBody;
import br.com.mfc_help.dto.pregnant.PregnantResponseBody;
import br.com.mfc_help.service.PregnantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/pregnant")
public class PregnantController {

    @Autowired
    private PregnantService pregnantService;

    @PostMapping
    public ResponseEntity<PregnantResponseBody> post(@RequestBody @Valid PregnantPostRequestBody pregnantPostRequestBody) {
        Pregnant savedPregnant = pregnantService.savePregnant(pregnantPostRequestBody);
        URI location = URI.create(String.format("/pregnant/%s", savedPregnant.getId().toString()));

        return ResponseEntity.created(location).body(new PregnantResponseBody(savedPregnant));
    }

    @PostMapping("anamnesis")
    public ResponseEntity<PregnantResponseBody> post(@RequestBody @Valid PregnantAnamnesisRequestBody pregnantAnamnesisRequestBody) {
        return ResponseEntity.ok(new PregnantResponseBody(pregnantService.doAnamnesis(pregnantAnamnesisRequestBody)));
    }

    @GetMapping("risk/{id}")
    public ResponseEntity<Integer> getPregnantTotalRiskById(@PathVariable String id) {
        return ResponseEntity.ok(pregnantService.calculateTotalRisk(UUID.fromString(id)));
    }

}
