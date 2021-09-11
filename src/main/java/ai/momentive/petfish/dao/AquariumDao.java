package ai.momentive.petfish.dao;

import ai.momentive.petfish.model.Aquarium;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AquariumDao extends CrudRepository<Aquarium, Long> {
}
