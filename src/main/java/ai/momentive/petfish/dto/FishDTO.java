package ai.momentive.petfish.dto;

import ai.momentive.petfish.enums.Species;
import lombok.Data;

@Data
public class FishDTO {
    private Long id;
    private Long aquariumId;
    private Species species;
    private String color;
    private Integer numberOfFins;
}
