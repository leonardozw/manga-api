package com.example.mangaapi.controller;

import static com.example.mangaapi.common.MangaConstants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.mangaapi.entity.Manga;
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

}
