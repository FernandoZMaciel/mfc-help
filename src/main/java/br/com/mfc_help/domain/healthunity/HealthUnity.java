package br.com.mfc_help.domain.healthunity;

import br.com.mfc_help.domain.doctor.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "health_unity")
@Entity(name = "HealthUnity")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class HealthUnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("codigo_cnes")
    private Long CNES;

    @JsonProperty("nome_razao_social")
    private String name;

    @JsonProperty("codigo_cep_estabelecimento")
    private String zipcode;

    @JsonProperty("numero_estabelecimento")
    private String addressNumber;

    @ManyToMany(mappedBy = "healthUnities")
    private List<User> users;

}
