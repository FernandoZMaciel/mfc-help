package br.com.mfc_help.repository;

import br.com.mfc_help.domain.pregnant.Pregnant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PregnantRepository extends JpaRepository<Pregnant, UUID> {
    // Métodos customizados podem ser adicionados aqui
}
