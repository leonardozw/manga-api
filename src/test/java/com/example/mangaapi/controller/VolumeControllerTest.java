package com.example.mangaapi.controller;

import static com.example.mangaapi.common.MangaConstants.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.mangaapi.entity.Chapter;
import com.example.mangaapi.entity.Volume;
import com.example.mangaapi.service.VolumeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(VolumeController.class)
public class VolumeControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private VolumeService volumeService;

        @Test
        public void createVolume_WithValidData_ReturnsCreated() throws Exception {
                when(volumeService.create(VOLUME_ONE)).thenReturn(VOLUME_ONE);
                mockMvc.perform(
                        post("/volumes")
                                .content(objectMapper.writeValueAsString(VOLUME_ONE))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
        }

        @Test
        public void createVolume_WithInvalidData_ReturnsUnprocessableEntity() throws Exception {
                Volume emptyVolume = new Volume();

                mockMvc.perform(
                                post("/volumes").content(objectMapper.writeValueAsString(emptyVolume))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isUnprocessableEntity());
        }

        @Test
        public void addChapter_WithValidArguments_ReturnsOk() throws Exception {
                Volume volume = EXPECTED_VOLUME;
                Chapter chapter = CHAPTER_ONE;

                when(volumeService.addChapter(volume.getId(), chapter)).thenReturn(EXPECTED_VOLUME);

                mockMvc.perform(post("/volumes/add/" + volume.getId())
                                .content(objectMapper.writeValueAsString(chapter))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());
        }

        @Test
        public void addChapter_WithInvalidArguments_ReturnsBadRequest() throws Exception {
                Chapter emptyChapter = new Chapter();

                mockMvc.perform(post("/volumes/add/" + 1L)
                                .content(objectMapper.writeValueAsString(emptyChapter))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isUnprocessableEntity());
        }

        @Test
        public void getAllVolumes_ReturnsOk() throws Exception{
                when(volumeService.getAll()).thenReturn(VALID_VCLUMES);

                mockMvc.perform(
                get("/volumes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        }

        @Test
        public void getAllVolumes_ReturnsEmptyVolumesAndOk() throws Exception{
                when(volumeService.getAll()).thenReturn(Collections.EMPTY_LIST);

                mockMvc.perform(
                get("/volumes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        }

        @Test
        public void getVolume_ByExistingId_ReturnsVolume() throws Exception{
                when(volumeService.getById(1L)).thenReturn(Optional.of(VOLUME_ONE));
                
                mockMvc.perform(
                        get("/volumes/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(VOLUME_ONE)));;
        }

        @Test
        public void getVolume_ByUnexistingId_ReturnsNotFound() throws Exception {
                mockMvc.perform(
                                get("/volumes/1"))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void getVolume_ByExistingTitle_ReturnsVolume() throws Exception{
                when(volumeService.getByTitle(VOLUME_ONE.getTitle())).thenReturn(Optional.of(VOLUME_ONE));
                
                mockMvc.perform(
                        get("/volumes/title/" + VOLUME_ONE.getTitle()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(VOLUME_ONE)));;
        }

        @Test
        public void getVolume_ByUnexistingTitle_ReturnsNotFound() throws Exception {
                mockMvc.perform(
                                get("/volumes/title/" + VOLUME_ONE.getTitle()))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void listVolumes_ByExistingManga_ReturnsVolumes() throws Exception{
                when(volumeService.listByMangaId(1L)).thenReturn(VALID_VCLUMES);
                
                mockMvc.perform(
                        get("/volumes/manga/" + 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(VALID_VCLUMES)));;
        }

        @Test
        public void listVolumes_ByUnexistingManga_ReturnsNotFound() throws Exception {
                mockMvc.perform(
                                get("/volumes/manga/" + 99L))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void updateVolume_WithValidData_ReturnsOk() throws Exception {

                Volume volume = new Volume();
                volume.setId(1L);
                volume.setTitle("Volume 1");

                when(volumeService.update(volume)).thenReturn(volume);

                mockMvc.perform(put("/volumes")
                                .content(objectMapper.writeValueAsString(volume))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());
        }

        @Test
        public void updateVolume_WithInvalidData_ReturnsBadRequest() throws Exception {

                Volume volume = new Volume();
                volume.setId(1L);
                volume.setTitle("");

                when(volumeService.update(volume)).thenReturn(volume);

                mockMvc.perform(put("/volumes")
                                .content(objectMapper.writeValueAsString(volume))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isUnprocessableEntity());
        }

        @Test
        public void deleteVolume_WithValidData_ReturnsNoContent() throws Exception {
                Long volumeId = 1L;

                when(volumeService.delete(eq(volumeId))).thenReturn(Collections.emptyList());

                mockMvc.perform(delete("/volumes/" + volumeId))
                                .andExpect(status().isNoContent())
                                .andExpect(content().string(""));
        }

        @Test
        public void removeManga_WithUnexistingId_ReturnsNoContent() throws Exception {
                final Long volumeId = 1L;

                doThrow(new EmptyResultDataAccessException(1)).when(volumeService).delete(volumeId);

                mockMvc.perform(delete("/volumes/" + volumeId))
                                .andExpect(status().isNotFound());
        }

}
