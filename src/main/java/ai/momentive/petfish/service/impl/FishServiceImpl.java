package ai.momentive.petfish.service.impl;

import ai.momentive.petfish.dao.AquariumDao;
import ai.momentive.petfish.dao.FishDao;
import ai.momentive.petfish.dto.FishDTO;
import ai.momentive.petfish.enums.Species;
import ai.momentive.petfish.exception.AquariumNotFoundException;
import ai.momentive.petfish.exception.AquariumSizeException;
import ai.momentive.petfish.exception.FishConflictException;
import ai.momentive.petfish.exception.FishNotFoundException;
import ai.momentive.petfish.mapper.AquaMapper;
import ai.momentive.petfish.model.Aquarium;
import ai.momentive.petfish.model.Fish;
import ai.momentive.petfish.service.FishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ai.momentive.petfish.enums.Species.*;

@Service
public class FishServiceImpl implements FishService {
    private static final Double LIMIT_FOR_TRIPLE_FINS = 75.0;
    private final FishDao fishDao;
    private final AquariumDao aquariumDao;

    private static final AquaMapper mapper = AquaMapper.INSTANCE;

    public FishServiceImpl(FishDao fishDao, AquariumDao aquariumDao) {
        this.fishDao = fishDao;
        this.aquariumDao = aquariumDao;
    }

    @Override
    @Transactional
    public void createFish(Long aquariumId, FishDTO fishDTO) {
        Aquarium aquarium = aquariumDao.findById(aquariumId).orElseThrow(AquariumNotFoundException::new);
        Fish fish = mapper.fishDTOToFish(fishDTO);

        checkConstraints(fish, aquarium);
        fish.setAquarium(aquarium);
        fish = fishDao.save(fish);
        aquarium.getFishes().add(fish);
        aquariumDao.save(aquarium);
    }


    @Override
    @Transactional
    public void updateFish(Long aquariumId, Long fishId, FishDTO fishDTO) {
        Fish fish = fishDao.findByAquarium_IdAndId(aquariumId, fishId).orElseThrow(FishNotFoundException::new);

        if (!aquariumId.equals(fishDTO.getAquariumId())) {
            Aquarium newAquarium = aquariumDao.findById(fishDTO.getAquariumId()).orElseThrow(AquariumNotFoundException::new);

            checkConstraints(fish, newAquarium);
            fish.setAquarium(newAquarium);
            newAquarium.getFishes().add(fish);
            aquariumDao.save(newAquarium);
        }
        fish.setColor(fishDTO.getColor());
        fish.setSpecies(fishDTO.getSpecies());
        fish.setNumberOfFins(fishDTO.getNumberOfFins());
        fishDao.save(fish);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FishDTO> getFishes(Long aquariumId) {
        Aquarium aquarium = aquariumDao.findById(aquariumId).orElseThrow(AquariumNotFoundException::new);
        return mapper.fishToFishDTO(aquarium.getFishes());
    }

    @Override
    @Transactional(readOnly = true)
    public FishDTO getFish(Long aquariumId, Long fishId) {
        Fish fish = fishDao.findByAquarium_IdAndId(aquariumId, fishId).orElseThrow(FishNotFoundException::new);
        return mapper.fishToFishDTO(fish);
    }

    private void checkConstraints(Fish fish, Aquarium newAquarium) {
        boolean containsGoldFish = newAquarium.getFishes().stream().anyMatch(f -> Species.GOLDFISH.equals(f.getSpecies()));
        boolean containsGuppy = newAquarium.getFishes().stream().anyMatch(f -> Species.GUPPY.equals(f.getSpecies()));

        if (GOLDFISH.equals(fish.getSpecies()) && containsGuppy || GUPPY.equals(fish.getSpecies()) && containsGoldFish) {
            throw new FishConflictException();
        } else if (fish.getNumberOfFins() >= 3 && newAquarium.getSize() <= LIMIT_FOR_TRIPLE_FINS) {
            throw new AquariumSizeException();
        }

    }

}
