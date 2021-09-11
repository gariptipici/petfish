package ai.momentive.petfish.service;

import ai.momentive.petfish.dto.FishDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FishService {

    void createFish(Long aquariumId, FishDTO fishDTO);

    @Transactional
    void updateFish(Long aquariumId, Long fishId, FishDTO fishDTO);

    List<FishDTO> getFishes(Long aquariumId);

    @Transactional(readOnly = true)
    FishDTO getFish(Long aquariumId, Long fishId);
}
