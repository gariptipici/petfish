package ai.momentive.petfish.unit.controller;

import ai.momentive.petfish.controller.AquariumController;
import ai.momentive.petfish.dto.AquariumDTO;
import ai.momentive.petfish.enums.Unit;
import ai.momentive.petfish.exception.AquariumNotFoundException;
import ai.momentive.petfish.service.AquariumService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.when;

@WebMvcTest(AquariumController.class)
class AquariumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AquariumService aquariumService;

    AquariumDTO aquarium;
    AquariumDTO aquariumUS;

    List<AquariumDTO> aquariums;

    @BeforeEach
    public void init(){
        aquarium = new AquariumDTO();
        aquarium.setUnit(Unit.LITER);
        aquarium.setSize(11.1);

        aquariumUS = new AquariumDTO();
        aquariumUS.setUnit(Unit.GALLON);
        aquariumUS.setSize(11.1);

        aquariums = new ArrayList<>();
        aquariums.add(aquarium);
        aquariums.add(aquariumUS);

        when(aquariumService.getAquariums()).thenReturn(aquariums);
        when(aquariumService.getAquarium(2L)).thenThrow(new AquariumNotFoundException());
        doNothing().when(aquariumService).createAquarium(any());
    }

    @Test
    void getAquariumsTest() throws Exception {
        mockMvc.perform(get("/aquariums")).andDo(print()).andExpect(status().isOk());
        mockMvc.perform(get("/aquariums?locale=us")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getAquariumTest() throws Exception {
        when(aquariumService.getAquarium(1L)).thenReturn(aquariumUS);
        mockMvc.perform(get("/aquariums/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getAquariumLocaleUSTest() throws Exception {
        when(aquariumService.getAquarium(1L)).thenReturn(aquarium);
        mockMvc.perform(get("/aquariums/1?locale=us")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void createAquariumTest() throws Exception {

        mockMvc.perform(post("/aquariums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(aquarium))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


    @Test
    void getAquariumNotFoundTest() throws Exception {
        mockMvc.perform(get("/aquariums/2")).andDo(print()).andExpect(status().isNotFound());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
