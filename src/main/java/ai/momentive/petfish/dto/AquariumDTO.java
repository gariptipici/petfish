package ai.momentive.petfish.dto;

import ai.momentive.petfish.enums.Unit;
import lombok.Data;
import java.util.List;

@Data
public class AquariumDTO {
    private Long id;
    private String glassType;
    private String shape;
    private Double size;
    private Unit unit;
    private List<FishDTO> fishes;
}
