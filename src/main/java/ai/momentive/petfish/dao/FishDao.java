package ai.momentive.petfish.dao;

import ai.momentive.petfish.model.Fish;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FishDao extends CrudRepository<Fish, Long> {

    Optional<Fish> findByAquarium_IdAndId(Long aquariumId, Long fishId);
}
