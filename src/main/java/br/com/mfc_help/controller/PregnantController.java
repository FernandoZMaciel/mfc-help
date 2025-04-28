package br.com.mfc_help.controller;

import br.com.mfc_help.domain.pregnant.Pregnant;
import br.com.mfc_help.dto.pregnant.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<Pregnant> getById(@PathVariable String id) {
        return ResponseEntity.ok(pregnantService.findByIdOrThrowEntityNotFoundException(UUID.fromString(id)));
    }

    @PostMapping
    public ResponseEntity<PregnantResponseBody> post(@RequestBody @Valid PregnantPostRequestBody pregnantPostRequestBody) {
        Pregnant savedPregnant = pregnantService.savePregnant(pregnantPostRequestBody);
        URI location = URI.create(String.format("/pregnant/%s", savedPregnant.getId().toString()));

        return ResponseEntity.created(location).body(new PregnantResponseBody(savedPregnant));
    }

    @PutMapping
    public ResponseEntity<PregnantResponseBody> put(@RequestBody @Valid PregnantPutRequestBody pregnantPutRequestBody) {
        return ResponseEntity.ok(new PregnantResponseBody(pregnantService.updatePregnant(pregnantPutRequestBody)));
    }

    @PostMapping("anamnesis")
    public ResponseEntity<PregnantResponseBody> post(@RequestBody @Valid PregnantAnamnesisRequestBody pregnantAnamnesisRequestBody) {
        return ResponseEntity.ok(new PregnantResponseBody(pregnantService.doAnamnesis(pregnantAnamnesisRequestBody)));
    }

    @GetMapping("status/{id}")
    public ResponseEntity<PregnantStatusResponseBody> getPregnantStatus(@PathVariable String id) {
        Pregnant pregnant = pregnantService.findByIdOrThrowEntityNotFoundException(UUID.fromString(id));
        return ResponseEntity.ok(pregnantService.gerPregnantStatus(pregnant));
    }


}
