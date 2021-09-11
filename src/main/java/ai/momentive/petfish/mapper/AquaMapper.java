package ai.momentive.petfish.mapper;

import ai.momentive.petfish.dto.AquariumDTO;
import ai.momentive.petfish.dto.FishDTO;
import ai.momentive.petfish.model.Aquarium;
import ai.momentive.petfish.model.Fish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AquaMapper {
    AquaMapper INSTANCE = Mappers.getMapper(AquaMapper.class);

    @Mapping(source = "aquarium.id", target = "aquariumId")
    FishDTO fishToFishDTO(Fish fish);

    Fish fishDTOToFish(FishDTO fish);


    AquariumDTO aquariumToAquariumDTO(Aquarium aquarium);
    Aquarium aquariumDTOToAquarium(AquariumDTO aquarium);

    List<FishDTO> fishToFishDTO(List<Fish>fish);
    List<Fish> fishDTOToFish(List<FishDTO> fish);

    List<AquariumDTO> aquariumListToAquariumDTOList(Iterable<Aquarium> aquarium);
}
