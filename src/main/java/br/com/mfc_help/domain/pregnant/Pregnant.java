package br.com.mfc_help.domain.pregnant;


import br.com.mfc_help.domain.Race;
import br.com.mfc_help.domain.doctor.User;
import br.com.mfc_help.domain.healthunity.HealthUnity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Table(name = "pregnant")
@Entity(name = "Pregnant")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class Pregnant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private LocalDate birthDate;

    private String cns;

    private LocalDate lastMenstruationDate;

    private LocalDate firstUltrasoundDate;

    private Integer gestationalAgeInWeeks;

    private Integer gestationalAgeInDays;

    private String jobPosition;

    private Boolean plannedPregnancy;

    private Boolean dentalAvaliation;

    private int vaginalDeliveries;

    private int cesareanSections;

    private int abortions;

    private int fetalDeaths ;

    private Race race;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "baby_id", referencedColumnName = "id")
    private PregnantsBaby baby;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "pregnant_pregnant_risk",
        joinColumns = @JoinColumn(name = "pregnant_id"),
        inverseJoinColumns = @JoinColumn(name = "pregnant_risk_id")
    )

    private Set<PregnantRisk> pregnantRisks;

    @ManyToOne
    @JoinColumn(name = "id_health_unity", referencedColumnName = "id")
    private HealthUnity healthUnity;

    @ManyToOne
    @JoinColumn(name = "id_doctor", referencedColumnName = "id")
    private User user;
}

