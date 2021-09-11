package ai.momentive.petfish.unit.controller;

import ai.momentive.petfish.controller.FishController;
import ai.momentive.petfish.dto.FishDTO;
import ai.momentive.petfish.exception.AquariumSizeException;
import ai.momentive.petfish.exception.FishConflictException;
import ai.momentive.petfish.exception.FishNotFoundException;
import ai.momentive.petfish.service.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FishController.class)
class FishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FishService fishService;

    FishDTO fish;

    @BeforeEach
    public void init(){
        fish = new FishDTO();
        when(fishService.getFish(1L, 1L)).thenReturn(fish);
        when(fishService.getFish(1L, 2L)).thenThrow(new FishNotFoundException());
        when(fishService.getFishes(1L)).thenReturn(Collections.singletonList(fish));
        doThrow(new FishConflictException()).when(fishService).createFish(2L, fish);
        doThrow(new AquariumSizeException()).when(fishService).createFish(3L, fish);
    }

    @Test
    void getFishesTest() throws Exception{
        mockMvc.perform(get("/aquariums/1/fishes")).andDo(print()).andExpect(status().is2xxSuccessful());
    }

    @Test
    void getFish() throws Exception{
        mockMvc.perform(get("/aquariums/1/fishes/1")).andDo(print()).andExpect(status().is2xxSuccessful());
    }

    @Test
    void getFishAndGetException() throws Exception{
        mockMvc.perform(get("/aquariums/1/fishes/2")).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    void updateFishTest() throws Exception {

        mockMvc.perform(put("/aquariums/1/fishes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fish))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createFishTest() throws Exception {

        mockMvc.perform(post("/aquariums/1/fishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fish))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createFishConflictTest() throws Exception {

        mockMvc.perform(post("/aquariums/2/fishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fish))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void createFishConflictTest2() throws Exception {

        mockMvc.perform(post("/aquariums/3/fishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fish))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
