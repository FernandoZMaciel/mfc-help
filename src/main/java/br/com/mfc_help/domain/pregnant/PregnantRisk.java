package br.com.mfc_help.domain.pregnant;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Table(name = "pregnant_risk")
@Entity(name = "PregnantRisk")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class PregnantRisk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String description;

    private Integer risk;


}

