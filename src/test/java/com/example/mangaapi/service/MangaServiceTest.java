package com.example.mangaapi.service;

import static com.example.mangaapi.common.MangaConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.repository.MangaRepository;

import jakarta.persistence.EntityNotFoundException;

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

    @Test
    public void createManga_WithInvalidData_ThrowsException() {
        when(mangaRepository.save(INVALID_MANGA)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> mangaService.create(INVALID_MANGA)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getManga_ByExistingId_ReturnsManga() {
        when(mangaRepository.findById(1L)).thenReturn(Optional.of(MANGA_ONE));

        Optional<Manga> sut = mangaService.getById(1L);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(MANGA_ONE);
    }

    @Test
    public void getManga_ByUnexistingId_ReturnsEmpty() {
        when(mangaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Manga> sut = mangaService.getById(1L);

        assertThat(sut).isEmpty();
    }

    @Test
    public void getManga_ByExistingName_ReturnsManga() {
        when(mangaRepository.findByName(MANGA_ONE.getName())).thenReturn(Optional.of(MANGA_ONE));

        Optional<Manga> sut = mangaService.getByName(MANGA_ONE.getName());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(MANGA_ONE);
    }

    @Test
    public void getManga_ByUnexistingName_ReturnsEmpty() {
        when(mangaRepository.findByName(MANGA_ONE.getName())).thenReturn(Optional.empty());

        Optional<Manga> sut = mangaService.getByName(MANGA_ONE.getName());

        assertThat(sut).isEmpty();
    }

    @Test
    public void listMangas_ReturnsMangas() {
        when(mangaRepository.findAll(any(Sort.class))).thenReturn(VALID_MANGAS);

        List<Manga> sut = mangaService.list();

        assertThat(sut).isNotEmpty();
        assertThat(sut).isEqualTo(VALID_MANGAS);
    }

    @Test
    public void listMangas_ReturnsEmpty(){
        when(mangaRepository.findAll(any(Sort.class))).thenReturn(Collections.emptyList());

        List<Manga> sut = mangaService.list();

        assertThat(sut).isEmpty();
    }

    @Test
    public void updateManga_WithValidArguments_ReturnsManga() {
        Manga manga = MANGA_ONE;

        when(mangaRepository.save(manga)).thenReturn(manga);

        mangaService.create(manga);

        Manga updatedManga = UPDATE_MANGA;

        when(mangaRepository.save(updatedManga)).thenReturn(updatedManga);

        Manga sut = mangaService.update(updatedManga);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(updatedManga);
        assertThat(sut).isNotEqualTo(manga);
    }

    @Test
    public void updateManga_WithInvalidArguments_ThrowsException() {
        Manga manga = MANGA_ONE;

        when(mangaRepository.save(manga)).thenReturn(manga);

        mangaService.create(manga);

        Manga updatedManga = INVALID_MANGA;

        when(mangaRepository.save(updatedManga)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> mangaService.update(updatedManga)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void addVolume_WithValidArguments_ReturnsManga() {
        Manga manga = MANGA_TWO;
        when(mangaRepository.findById(1L)).thenReturn(Optional.of(manga));
        when(mangaRepository.save(manga)).thenReturn(EXPECTED_MANGA);

        Manga sut = mangaService.addVolume(1L, VOLUME_ONE);

        assertThat(sut).isEqualTo(EXPECTED_MANGA);
    }

    @Test
    public void addVolume_WitUnexistingManga_ThrowsException() {
        when(mangaRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThatThrownBy(() -> mangaService.addVolume(1L, VOLUME_ONE)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void removeManga_WithExistingId_doesNotThrowAnyException() {
        assertThatCode(() -> mangaService.delete(1L)).doesNotThrowAnyException();
    }

    @Test
    public void removeManga_WithUnexistingId_ThrowsException() {
        doThrow(new RuntimeException()).when(mangaRepository).deleteById(99L);
        assertThatThrownBy(() -> mangaService.delete(99L)).isInstanceOf(RuntimeException.class);
    }
}
