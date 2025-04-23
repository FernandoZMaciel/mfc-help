package br.com.mfc_help.repository;

import br.com.mfc_help.domain.healthunity.HealthUnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthUnityRepository extends JpaRepository<HealthUnity, Long> {
    HealthUnity findByCNES(Long cnes);
}
