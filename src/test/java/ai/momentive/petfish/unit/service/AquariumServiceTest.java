package ai.momentive.petfish.unit.service;

import ai.momentive.petfish.dao.AquariumDao;
import ai.momentive.petfish.dto.AquariumDTO;
import ai.momentive.petfish.exception.AquariumNotFoundException;
import ai.momentive.petfish.model.Aquarium;
import ai.momentive.petfish.model.Fish;
import ai.momentive.petfish.service.impl.AquariumServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AquariumServiceTest {

    AquariumDTO aqua1;

    List<Fish> fl1;
    Fish f1;
    Aquarium a1;

    private final AquariumDao aquariumDao = Mockito.mock(AquariumDao.class);
    AquariumServiceImpl aquariumService;


    @BeforeEach
    public void init(){
        aquariumService = new AquariumServiceImpl(aquariumDao);

        aqua1 = new AquariumDTO();
        aqua1.setGlassType("g1");
        aqua1.setShape("s1");
        aqua1.setSize(1.1);
        aqua1.setFishes(new ArrayList<>());

        a1 = new Aquarium();
        a1.setShape("s1");

        f1 = new Fish();
        fl1 = new ArrayList<>();
        fl1.add(f1);

        Mockito.when(aquariumDao.save(any())).thenReturn(new Aquarium());
        Mockito.when(aquariumDao.findAll()).thenReturn(Collections.singletonList(a1));
        Mockito.when(aquariumDao.findById(1L)).thenReturn(Optional.of(a1));


    }

    @Test
    void createAquariumTest(){
        aquariumService.createAquarium(aqua1);
        verify(aquariumDao, times(1)).save(any());
    }

    @Test
    void getAquariumsTest(){
        List<AquariumDTO> aquariums = aquariumService.getAquariums();
        Assertions.assertEquals(1, aquariums.size());
    }

    @Test
    void getAquariumTest(){
        AquariumDTO aquarium = aquariumService.getAquarium(1L);
        Assertions.assertEquals("s1", aquarium.getShape());
    }

    @Test
    void getAquariumNotFoundTest(){
        Assertions.assertThrows(AquariumNotFoundException.class, () ->  aquariumService.getAquarium(2L));
    }


}
