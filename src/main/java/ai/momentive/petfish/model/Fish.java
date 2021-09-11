package ai.momentive.petfish.model;

import ai.momentive.petfish.enums.Species;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@Data
@Entity
@Table(name = "FISH")
@NoArgsConstructor
public class Fish {
    @Id
    @GeneratedValue
    private Long id;

    private Species species;
    private String color;
    private Integer numberOfFins;

    @ManyToOne
    @JoinColumn(name="aquarium_id", nullable=false)
    private Aquarium aquarium;

}
