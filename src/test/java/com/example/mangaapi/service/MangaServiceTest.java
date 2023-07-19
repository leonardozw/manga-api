package com.example.mangaapi.service;

import static com.example.mangaapi.common.MangaConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.repository.MangaRepository;

@ExtendWith(MockitoExtension.class)
public class MangaServiceTest {

    @InjectMocks
    private MangaService mangaService;

    @Mock
    private MangaRepository mangaRepository;

    @Test
    public void createManga_WithValidData_ReturnsManga() {
        when(mangaRepository.save(MANGA_ONE)).thenReturn(MANGA_ONE);

        Manga sut = mangaService.create(MANGA_ONE);

        assertThat(sut).isEqualTo(MANGA_ONE);
    }

}
