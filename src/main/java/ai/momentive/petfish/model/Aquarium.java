package ai.momentive.petfish.model;

import ai.momentive.petfish.enums.Unit;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Data
@Entity
@Table(name = "AQUARIUM")
@NoArgsConstructor
public class Aquarium {
    @Id
    @GeneratedValue
    private Long id;

    private String glassType;
    private String shape;
    private Double size;
    private Unit unit;

    @OneToMany(mappedBy = "aquarium")
    private List<Fish> fishes;


}
