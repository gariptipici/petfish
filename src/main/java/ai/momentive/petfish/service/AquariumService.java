package ai.momentive.petfish.service;

import ai.momentive.petfish.dto.AquariumDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AquariumService {
    void createAquarium(AquariumDTO aquariumDto);

    List<AquariumDTO> getAquariums();

    AquariumDTO getAquarium(@PathVariable Long id);
}
