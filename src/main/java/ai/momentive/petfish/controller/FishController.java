package ai.momentive.petfish.controller;

import ai.momentive.petfish.dto.FishDTO;
import ai.momentive.petfish.service.FishService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("aquariums/{aquariumId}/fishes")
public class FishController {
    private final FishService fishService;

    public FishController(FishService fishService) {
        this.fishService = fishService;
    }

    @GetMapping(produces = "application/json")
    public List<FishDTO> getFishes(@PathVariable Long aquariumId){
        return fishService.getFishes(aquariumId);
    }

    @GetMapping(value = "/{fishId}", produces = "application/json")
    public FishDTO getFish(@PathVariable Long aquariumId, @PathVariable Long fishId){
        return fishService.getFish(aquariumId, fishId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void createFish(@PathVariable Long aquariumId,  @RequestBody FishDTO fishDTO){
        fishService.createFish(aquariumId, fishDTO);
    }

    @PutMapping(value = "/{fishId}", consumes = "application/json")
    public void updateFish(@PathVariable Long aquariumId, @PathVariable Long fishId,  @RequestBody FishDTO fishDTO){
        fishService.updateFish(aquariumId, fishId, fishDTO);
    }
}
