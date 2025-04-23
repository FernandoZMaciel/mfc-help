package br.com.mfc_help.controller;

import br.com.mfc_help.domain.healthunity.HealthUnity;
import br.com.mfc_help.service.HealthUnityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-unity")
public class HealthUnityController {

    @Autowired
    private HealthUnityService healthUnityService;

    @GetMapping("/{cnes}")
    public ResponseEntity<HealthUnity> getHealthUnity(@PathVariable Long cnes) {
        HealthUnity healthUnity = healthUnityService.getHealthUnity(cnes);
        return ResponseEntity.ok(healthUnity);
    }
}
