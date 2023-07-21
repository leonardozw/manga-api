package com.example.mangaapi.controller;

import static com.example.mangaapi.common.MangaConstants.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.entity.Volume;
import com.example.mangaapi.service.MangaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MangaController.class)
public class MangaControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private MangaService mangaService;

        @Test
        public void createManga_WithValidData_ReturnsCreated() throws Exception {
                when(mangaService.create(MANGA_ONE)).thenReturn(MANGA_ONE);
                mockMvc.perform(
                        post("/mangas")
                                .content(objectMapper.writeValueAsString(MANGA_ONE))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(content().json(objectMapper.writeValueAsString(MANGA_ONE)));
        }

        @Test
        public void createManga_WithInvalidData_ReturnsBadRequest() throws Exception {
                Manga emptyManga = new Manga();

                // when(mangaService.create(MANGA_ONE)).thenReturn(MANGA_ONE);
                mockMvc.perform(
                                post("/mangas").content(objectMapper.writeValueAsString(emptyManga))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isUnprocessableEntity());
                mockMvc.perform(
                                post("/mangas").content(objectMapper.writeValueAsString(INVALID_MANGA))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isUnprocessableEntity());
        }

        @Test
        public void getManga_ByExistingId_ReturnsManga() throws Exception{
                when(mangaService.getById(1L)).thenReturn(Optional.of(MANGA_ONE));
                
                mockMvc.perform(
                        get("/mangas/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(MANGA_ONE)));;
        }

        @Test
        public void getManga_ByUnexistingId_ReturnsNotFound() throws Exception {
                mockMvc.perform(
                                get("/mangas/1"))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void getManga_ByExistingName_ReturnsManga() throws Exception{
                when(mangaService.getByName(MANGA_ONE.getName())).thenReturn(Optional.of(MANGA_ONE));

                mockMvc.perform(
                        get("/mangas/name/" + MANGA_ONE.getName()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(MANGA_ONE)));;
        }

        @Test
        public void getManga_ByUnexistingName_ReturnsNotFound() throws Exception {

                mockMvc.perform(
                                get("/mangas/name/" + MANGA_ONE.getName()))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void listMangas_ReturnsMangas() throws Exception{
                when(mangaService.list()).thenReturn(VALID_MANGAS);

                mockMvc.perform(
                get("/mangas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        }

        @Test
        public void listMangas_ReturnsNoMangas() throws Exception{
                when(mangaService.list()).thenReturn(Collections.EMPTY_LIST);

                mockMvc.perform(
                get("/mangas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
        }

        @Test
        public void addVolume_WithValidArguments_ReturnsManga() throws Exception {
                Manga manga = UPDATE_MANGA;
                Volume volume = VOLUME_ONE;

                when(mangaService.addVolume(manga.getId(), volume)).thenReturn(EXPECTED_MANGA);

                mockMvc.perform(post("/mangas/" + manga.getId() + "/add")
                                .content(objectMapper.writeValueAsString(volume))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());
        }

        @Test
        public void addVolume_WithInvalidArguments_ReturnsBadRequest() throws Exception {
                Volume emptyVolume = new Volume();

                mockMvc.perform(post("/mangas/" + 1L + "/add")
                                .content(objectMapper.writeValueAsString(emptyVolume))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isUnprocessableEntity());

        }

        @Test
        public void removeManga_WithExistingId_ReturnsNoContent() throws Exception {
                List<Manga> updatedMangas = new ArrayList<>();

                when(mangaService.delete(1L)).thenReturn(updatedMangas);

                mockMvc.perform(delete("/mangas/1"))
                                .andExpect(status().isNoContent());
        }

        @Test
        public void removeManga_WithUnexistingId_ReturnsNoContent() throws Exception {
                final Long mangaId = 1L;

                doThrow(new EmptyResultDataAccessException(1)).when(mangaService).delete(mangaId);

                mockMvc.perform(delete("/mangas/" + mangaId))
                                .andExpect(status().isNotFound());
        }

}
