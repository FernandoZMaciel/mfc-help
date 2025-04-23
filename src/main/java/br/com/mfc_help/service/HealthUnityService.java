package br.com.mfc_help.service;
import br.com.mfc_help.domain.healthunity.HealthUnity;
import br.com.mfc_help.infra.exception.BadRequestException;
import br.com.mfc_help.repository.HealthUnityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class HealthUnityService {

    @Autowired
    private HealthUnityRepository healthUnityRepository;

    private static final String API_URL = "https://apidadosabertos.saude.gov.br/cnes/estabelecimentos/";

    private HealthUnity fetchHealthUnity(String cnes) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(API_URL + cnes, HealthUnity.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError() && e.getResponseBodyAsString().contains("\"message\": \"Estabelecimento not found.\"")) {
                return null;
            }
            throw e;
        }
    }

    public HealthUnity getHealthUnity(Long cnes){
        HealthUnity healthUnity = healthUnityRepository.findByCNES(cnes);
        if (healthUnity == null) {
            healthUnity = fetchHealthUnity(cnes.toString());

            if (healthUnity == null) {
                throw new EntityNotFoundException("Health Unity not found");
            }

            if (healthUnity != null) {
                healthUnityRepository.save(healthUnity);
            }

        }
        return healthUnity;
    }


}
