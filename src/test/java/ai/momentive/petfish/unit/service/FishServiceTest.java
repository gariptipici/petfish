package ai.momentive.petfish.unit.service;

import ai.momentive.petfish.dao.AquariumDao;
import ai.momentive.petfish.dao.FishDao;
import ai.momentive.petfish.dto.FishDTO;
import ai.momentive.petfish.enums.Species;
import ai.momentive.petfish.exception.AquariumNotFoundException;
import ai.momentive.petfish.exception.AquariumSizeException;
import ai.momentive.petfish.exception.FishConflictException;
import ai.momentive.petfish.exception.FishNotFoundException;
import ai.momentive.petfish.model.Aquarium;
import ai.momentive.petfish.model.Fish;
import ai.momentive.petfish.service.impl.FishServiceImpl;
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
class FishServiceTest {

    private final FishDao fishDao = Mockito.mock(FishDao.class);
    private final AquariumDao aquariumDao = Mockito.mock(AquariumDao.class);

    FishServiceImpl fishService;

    Fish f1;
    Fish f2;
    Aquarium a1;
    List<Fish> fishes;
    FishDTO fish;

    FishDTO guppy;
    FishDTO goldFish;

    Aquarium aquaWithGuppy;
    Aquarium aquaWithGoldFish;
    Aquarium aquaSmallerThan75;

    List<Fish> fishesWithGuppy;
    List<Fish> fishesWithGoldFish;


    @BeforeEach
    public void init(){
        fishService = new FishServiceImpl(fishDao, aquariumDao);

        a1 = new Aquarium();
        f1 = new Fish();
        f2 = new Fish();
        f2.setNumberOfFins(2);
        f1.setNumberOfFins(1);
        fishes = new ArrayList<>();
        fishes.add(f1);
        fishes.add(f2);
        a1.setFishes(fishes);
        f1.setAquarium(a1);

        fish = new FishDTO();
        fish.setNumberOfFins(2);
        fish.setAquariumId(2L);

        guppy = new FishDTO();
        guppy.setNumberOfFins(2);
        guppy.setSpecies(Species.GUPPY);

        goldFish = new FishDTO();
        goldFish.setNumberOfFins(4);
        goldFish.setSpecies(Species.GOLDFISH);

        aquaWithGuppy = new Aquarium();
        aquaWithGoldFish = new Aquarium();
        aquaSmallerThan75 = new Aquarium();

        fishesWithGuppy = new ArrayList<>();
        fishesWithGuppy.add(new Fish(1L, Species.GUPPY, "", 2, aquaWithGuppy));
        fishesWithGuppy.add(new Fish(1L, Species.HAMSI, "", 2, aquaWithGuppy));

        fishesWithGoldFish = new ArrayList<>();
        fishesWithGoldFish.add(new Fish(1L, Species.GOLDFISH, "", 2, aquaWithGoldFish));
        fishesWithGoldFish.add(new Fish(1L, Species.HAMSI, "", 2, aquaWithGoldFish));

        aquaWithGuppy.setFishes(fishesWithGuppy);
        aquaWithGoldFish.setFishes(fishesWithGoldFish);
        aquaSmallerThan75.setFishes(new ArrayList<>());
        aquaSmallerThan75.setSize(20.5);

        Mockito.when(fishDao.findByAquarium_IdAndId(1L, 1L)).thenReturn(Optional.of(f1));


    }

    @Test
    void getFishTest(){
        Mockito.when(aquariumDao.findById(any())).thenReturn(Optional.of(a1));
        FishDTO fish = fishService.getFish(1L, 1L);
        Assertions.assertEquals(1, fish.getNumberOfFins());
    }

    @Test
    void getFishNotFoundTest(){
        Assertions.assertThrows(FishNotFoundException.class, () ->  fishService.getFish(1L, 2L));
    }

    @Test
    void getFishesTest(){
        Mockito.when(aquariumDao.findById(any())).thenReturn(Optional.of(a1));
        List<FishDTO> fishes = fishService.getFishes(1L);
        Assertions.assertEquals(2, fishes.size());
    }

    @Test
    void createFishTest(){
        Mockito.when(aquariumDao.findById(any())).thenReturn(Optional.of(a1));
        fishService.createFish(1L, fish);
        verify(fishDao, times(1)).save(any());
    }

    @Test()
    void createGoldFishInGuppies(){
        Mockito.when(aquariumDao.findById(any())).thenReturn(Optional.of(aquaWithGuppy));
        Assertions.assertThrows(FishConflictException.class, () ->  fishService.createFish(1L, goldFish));
    }

    @Test()
    void createGuppyInGoldFishes(){
        Mockito.when(aquariumDao.findById(any())).thenReturn(Optional.of(aquaWithGoldFish));
        Assertions.assertThrows(FishConflictException.class, () ->  fishService.createFish(1L, guppy));
    }

    @Test()
    void createBigFishInSmallTank(){
        Mockito.when(aquariumDao.findById(any())).thenReturn(Optional.of(aquaSmallerThan75));
        Assertions.assertThrows(AquariumSizeException.class, () ->  fishService.createFish(1L, goldFish));
    }

    @Test
    void updateFishTest(){
        Mockito.when(aquariumDao.findById(any())).thenReturn(Optional.of(a1));
        Mockito.when(fishDao.findByAquarium_IdAndId(any(), any())).thenReturn(Optional.of(f1));
        fishService.updateFish(1L, 1L, fish);
        verify(fishDao, times(1)).save(any());
    }
}
