package br.com.mfc_help.repository;

import br.com.mfc_help.domain.pregnant.Pregnant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PregnantRepository extends JpaRepository<Pregnant, UUID> {

    boolean existsByCns(String cns);

    boolean existsByCnsAndIdNot(String cns, UUID id);
}
