package ai.momentive.petfish;

import ai.momentive.petfish.controller.AquariumController;
import ai.momentive.petfish.controller.FishController;
import ai.momentive.petfish.dao.AquariumDao;
import ai.momentive.petfish.dao.FishDao;
import ai.momentive.petfish.service.AquariumService;
import ai.momentive.petfish.service.FishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class ApplicationTests {

    @Autowired
    AquariumController aquariumController;

    @Autowired
    FishController fishController;

    @Autowired
    AquariumService aquariumService;

    @Autowired
    FishService fishService;

    @Autowired
    AquariumDao aquariumDao;

    @Autowired
    FishDao fishDao;

    @Test
    void contextLoads() {
        assertThat(aquariumController).isNotNull();
        assertThat(fishController).isNotNull();
        assertThat(aquariumService).isNotNull();
        assertThat(fishService).isNotNull();
        assertThat(aquariumDao).isNotNull();
        assertThat(fishDao).isNotNull();
    }

}
