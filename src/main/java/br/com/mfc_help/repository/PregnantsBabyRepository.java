package br.com.mfc_help.repository;

import br.com.mfc_help.domain.pregnant.PregnantsBaby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PregnantsBabyRepository extends JpaRepository<PregnantsBaby, UUID> {
    // Métodos customizados podem ser adicionados aqui
}
