package com.example.mangaapi.service;

import static com.example.mangaapi.common.MangaConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;

import com.example.mangaapi.entity.Volume;
import com.example.mangaapi.repository.MangaRepository;
import com.example.mangaapi.repository.VolumeRepository;

@ExtendWith(MockitoExtension.class)
public class VolumeServiceTest {

    @InjectMocks
    private VolumeService volumeService;

    @Mock
    private VolumeRepository volumeRepository;

    @Mock
    private MangaRepository mangaRepository;

    @Test
    public void createVolume_WithValidData_ReturnsVolume(){
        when(volumeRepository.save(VOLUME_ONE)).thenReturn(VOLUME_ONE);

        Volume sut = volumeService.create(VOLUME_ONE);

        assertThat(sut).isEqualTo(VOLUME_ONE);
    }

    @Test
    public void createVolume_WithInvalidData_ThrowsException(){
        when(volumeRepository.save(INVALID_VOLUME)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> volumeService.create(INVALID_VOLUME)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void addChapter_ToExistingVolumeWithValidData_ReturnsVolume(){
        when(volumeRepository.findById(1L)).thenReturn(Optional.of(VOLUME_ONE));
        when(volumeRepository.save(VOLUME_ONE)).thenReturn(VOLUME_ONE);

        Volume sut = volumeService.addChapter(1L, CHAPTER_ONE);

        assertThat(sut).isEqualTo(VOLUME_ONE);
    }

    @Test
    public void addChapter_ToUnexistingVolume_ThrowsException(){
        when(volumeRepository.findById(99L)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> volumeService.addChapter(99L, CHAPTER_ONE)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void addChapter_WithInvalidData_ThrowsException() {
        assertThatThrownBy(() -> volumeService.addChapter(1L, INVALID_CHAPTER)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getVolume_ByExistingId_ReturnsVolume(){
        when(volumeRepository.findById(1L)).thenReturn(Optional.of(VOLUME_ONE));

        Optional<Volume> sut = volumeService.getById(1L);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(VOLUME_ONE);
    }

    @Test
    public void getVolume_ByUnexistingId_ReturnsEmpty(){
        when(volumeRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Volume> sut = volumeService.getById(99L);

        assertThat(sut).isEmpty();
    }

    @Test
    public void getVolume_ByExistingTitle_ReturnsVolume(){
        when(volumeRepository.findByTitle(VOLUME_ONE.getTitle())).thenReturn(Optional.of(VOLUME_ONE));

        Optional<Volume> sut = volumeService.getByTitle(VOLUME_ONE.getTitle());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(VOLUME_ONE);
    }

    @Test
    public void getVolume_ByUnexistingTitle_ReturnsEmpty(){
        when(volumeRepository.findByTitle("Unexisting title")).thenReturn(Optional.empty());

        Optional<Volume> sut = volumeService.getByTitle("Unexisting title");

        assertThat(sut).isEmpty();
    }

    @Test
    public void listVolumes_FromExistingManga_ReturnsVolumes() {
        when(mangaRepository.findById(1L)).thenReturn(Optional.of(MANGA_ONE));
        when(volumeRepository.findAllByManga(eq(MANGA_ONE), any(Sort.class))).thenReturn(VALID_VCLUMES);

        List<Volume> sut = volumeService.listByMangaId(1L);

        assertThat(sut).isNotEmpty();
        assertThat(sut).isEqualTo(VALID_VCLUMES);
    }

    @Test
    public void listVolumes_FromUnexistingManga_ReturnsEmpty() {
        when(mangaRepository.findById(99L)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> volumeService.listByMangaId(99L)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void updateVolume_WithValidData_ReturnsVolume() {
        when(volumeRepository.save(VOLUME_ONE)).thenReturn(VOLUME_ONE);

        Volume volume = volumeService.create(VOLUME_ONE);

        volume.setTitle("new title");

        Volume sut = volumeService.update(volume);

        assertThat(sut).isEqualTo(volume);
    }

    @Test
    public void updateVolume_WithInvalidData_ThrowsException() {
        Volume volume = new Volume();

        volume.setTitle("");

        doThrow(DataIntegrityViolationException.class).when(volumeRepository).save(volume);

        assertThatThrownBy(() -> volumeService.update(volume)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void deleteVolume_ByExistingId_ReturnsVolumes() {
        assertThatCode(() -> volumeService.delete(1L)).doesNotThrowAnyException();

        verify(volumeRepository).deleteById(1L);

        verify(volumeRepository).findAll();
    }

    @Test
    public void deleteVolume_ByUnexistingId_ThrowsException() {
        doThrow(new RuntimeException()).when(volumeRepository).deleteById(99L);
        assertThatThrownBy(() -> volumeService.delete(99L)).isInstanceOf(RuntimeException.class);
    }

}
