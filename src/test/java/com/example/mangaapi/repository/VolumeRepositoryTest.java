package com.example.mangaapi.repository;

import static com.example.mangaapi.common.MangaConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;

import com.example.mangaapi.entity.Chapter;
import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.entity.Volume;

@DataJpaTest
public class VolumeRepositoryTest {

    @Autowired
    private VolumeRepository volumeRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void afterEach() {
        VOLUME_ONE.setId(null);
        VOLUME_ONE.setChapters(new ArrayList<>());
    }

    @Test
    public void createVolume_WithValidData_ReturnsVolume() {
        Volume volume = volumeRepository.save(VOLUME_ONE);

        Volume sut = testEntityManager.find(Volume.class, volume.getId());

        assertThat(sut.getId()).isNotNull();
        assertThat(sut.getIssue()).isEqualTo(VOLUME_ONE.getIssue());
        assertThat(sut.getTitle()).isEqualTo(VOLUME_ONE.getTitle());
        assertThat(sut.getManga()).isEqualTo(VOLUME_ONE.getManga());
        assertThat(sut.getChapters()).isEqualTo(VOLUME_ONE.getChapters());
        assertThat(sut.getCover()).isEqualTo(VOLUME_ONE.getCover());
    }

    @Test
    public void createVolume_WithInvalidData_ThrowsException() {
        Volume invalidVolume = INVALID_VOLUME;

        assertThatThrownBy(() -> volumeRepository.save(invalidVolume)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void addChapter_ToExistingVolumeWithValidData_ReturnsVolume() {
        Volume vol = testEntityManager.merge(VOLUME_ONE);
        Chapter chp = CHAPTER_ONE;

        Optional<Volume> sut = volumeRepository.findById(vol.getId());

        sut.get().getChapters().add(0, chp);
        chp.setVolume(sut.get());

        assertThat(sut.get().getChapters()).hasSize(1);
        assertThat(sut.get().getChapters().get(0)).isEqualTo(CHAPTER_ONE);
    }

    @Test
    public void addChapter_ToUnexistingVolume_ThrowsException() {
        Optional<Volume> volume = volumeRepository.findById(1L);

        assertThat(volume).isEmpty();
    }

    @Test
    public void getVolume_ByExistingId_ReturnsVolume() {
        Volume vol = testEntityManager.merge(VOLUME_ONE);
        Optional<Volume> volume = volumeRepository.findById(vol.getId());

        assertThat(volume).isNotEmpty();
        assertThat(volume.get()).isEqualTo(vol);
    }

    @Test
    public void getVolume_ByUnexistingId_ReturnsEmpty() {
        Optional<Volume> volume = volumeRepository.findById(1L);

        assertThat(volume).isEmpty();
    }

    @Test
    public void getVolume_ByExistingTitle_ReturnsVolume() {
        Volume vol = testEntityManager.merge(VOLUME_TWO);
        Optional<Volume> volume = volumeRepository.findByTitle(vol.getTitle());

        assertThat(volume).isNotEmpty();
        assertThat(volume.get()).isEqualTo(vol);
    }

    @Test
    public void getVolume_ByUnexistingTitle_ReturnsEmpty() {
        Optional<Volume> volume = volumeRepository.findByTitle("name");

        assertThat(volume).isEmpty();
    }

    @Test
    public void listVolumes_FromExistingManga_ReturnsVolumes() {
        Manga manga = MANGA_ONE;
        manga = testEntityManager.persist(manga);

        Volume volume1 = new Volume(1, "Volume 1", manga, null, null);
        Volume volume2 = new Volume(2, "Volume 2", manga, null, null);
        testEntityManager.persist(volume1);
        testEntityManager.persist(volume2);

        Sort sort = Sort.by(Sort.Direction.ASC, "issue");

        List<Volume> volumes = volumeRepository.findAllByManga(manga, sort);

        assertThat(volumes).isNotNull();
        assertThat(volumes).hasSize(2);
        assertThat(volumes).containsExactly(volume1, volume2);
    }

    @Test
    public void listVolumes_FromUnexistingManga_ReturnsEmpty() {
        Manga manga = new Manga();

        Sort sort = Sort.by(Sort.Direction.ASC, "issue");

        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            volumeRepository.findAllByManga(manga, sort);
        });
    }

    @Test
    public void deleteVolume_ByExistingId_ReturnsVolumes() {
        Volume volume = testEntityManager.merge(VOLUME_ONE);

        volumeRepository.deleteById(volume.getId());

        Volume removedVolume = testEntityManager.find(Volume.class, volume.getId());

        assertThat(removedVolume).isNull();
    }

    @Test
    public void deleteVolume_ByUnexistingId_ThrowsException() {
        Long id = 1L;

        Optional<Volume> volume = volumeRepository.findById(id);

        if (volume == null) {
            assertThrows(EmptyResultDataAccessException.class, () -> {
                volumeRepository.deleteById(id);
            });
        }
    }
}
