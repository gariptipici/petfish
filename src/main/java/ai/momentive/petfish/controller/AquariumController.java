package ai.momentive.petfish.controller;

import ai.momentive.petfish.dto.AquariumDTO;
import ai.momentive.petfish.enums.Unit;
import ai.momentive.petfish.service.AquariumService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("aquariums")
public class AquariumController {

    private static final String LOCALE_US = "US";
    private final AquariumService aquariumService;

    public AquariumController(AquariumService aquariumService) {
        this.aquariumService = aquariumService;
    }

    @GetMapping(produces = "application/json")
    public List<AquariumDTO> getAquariums(@RequestParam(required = false) String locale){
        if(LOCALE_US.equalsIgnoreCase(locale)) {
            return aquariumService.getAquariums().stream().map(aqua -> {
                if (Unit.LITER.equals(aqua.getUnit())){
                    aqua.setSize(literToGallonConverter(aqua.getSize()));
                    aqua.setUnit(Unit.GALLON);
                }
                return aqua;
            }).collect(Collectors.toList());
        } else {
            return aquariumService.getAquariums().stream().map(aqua -> {
                if (Unit.GALLON.equals(aqua.getUnit())){
                    aqua.setSize(gallonToLiterConverter(aqua.getSize()));
                    aqua.setUnit(Unit.LITER);
                }
                return aqua;
            }).collect(Collectors.toList());
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public AquariumDTO getAquarium(@PathVariable Long id, @RequestParam(required = false) String locale){
        AquariumDTO aqua = aquariumService.getAquarium(id);
        if(LOCALE_US.equalsIgnoreCase(locale)) {
            if (Unit.LITER.equals(aqua.getUnit())){
                aqua.setSize(literToGallonConverter(aqua.getSize()));
                aqua.setUnit(Unit.GALLON);
            }
        } else {
            if (Unit.GALLON.equals(aqua.getUnit())){
                aqua.setSize(gallonToLiterConverter(aqua.getSize()));
                aqua.setUnit(Unit.LITER);
            }
        }
        return aqua;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAquarium(@RequestBody AquariumDTO aquariumDto){
        aquariumService.createAquarium(aquariumDto);
    }

    private Double literToGallonConverter(Double liter){
        return 0.264172 * liter;
    }

    private Double gallonToLiterConverter(Double gallon){
        return 3.78541 * gallon;
    }
}
