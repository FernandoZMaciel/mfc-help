package br.com.mfc_help.repository;

import br.com.mfc_help.domain.pregnant.PregnantRisk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PregnantRiskRepository extends JpaRepository<PregnantRisk, Long> {
    // Métodos customizados podem ser adicionados aqui
}
