package ai.momentive.petfish.service.impl;

import ai.momentive.petfish.dao.AquariumDao;
import ai.momentive.petfish.dto.AquariumDTO;
import ai.momentive.petfish.exception.AquariumNotFoundException;
import ai.momentive.petfish.mapper.AquaMapper;
import ai.momentive.petfish.model.Aquarium;
import ai.momentive.petfish.service.AquariumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class AquariumServiceImpl implements AquariumService {

    private final AquariumDao aquariumDao;

    private static final AquaMapper mapper = AquaMapper.INSTANCE;

    public AquariumServiceImpl(AquariumDao aquariumDao) {
        this.aquariumDao = aquariumDao;
    }

    @Override
    @Transactional
    public void createAquarium(AquariumDTO aquariumDto){
        Aquarium aquarium = mapper.aquariumDTOToAquarium(aquariumDto);
        aquariumDao.save(aquarium);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AquariumDTO> getAquariums(){
        Iterable<Aquarium> aquariumList = aquariumDao.findAll();
        return mapper.aquariumListToAquariumDTOList(aquariumList);
    }

    @Override
    @Transactional(readOnly = true)
    public AquariumDTO getAquarium(@PathVariable Long id){
        Aquarium aquarium = aquariumDao.findById(id).orElseThrow(AquariumNotFoundException::new);
        return mapper.aquariumToAquariumDTO(aquarium);
    }
}
