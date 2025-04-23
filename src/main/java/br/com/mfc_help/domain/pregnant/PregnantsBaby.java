package br.com.mfc_help.domain.pregnant;


import br.com.mfc_help.domain.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "pregnant_baby")
@Entity(name = "PregnantBaby")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class PregnantsBaby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    private Gender gender;
}
