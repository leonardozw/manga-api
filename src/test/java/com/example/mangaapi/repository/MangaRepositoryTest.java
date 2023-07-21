package com.example.mangaapi.repository;

import static com.example.mangaapi.common.MangaConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;

import com.example.mangaapi.entity.Manga;

@DataJpaTest
public class MangaRepositoryTest {
    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void afterEach() {
        MANGA_ONE.setId(null);
        MANGA_ONE.setVolumes(null);
    }

    @Test
    public void createManga_WithValidData_ReturnsManga() {
        Manga manga = mangaRepository.save(MANGA_ONE);

        Manga sut = testEntityManager.find(Manga.class, manga.getId());

        assertThat(sut.getId()).isNotNull();
        assertThat(sut.getName()).isEqualTo(MANGA_ONE.getName());
        assertThat(sut.getDescription()).isEqualTo(MANGA_ONE.getDescription());
        assertThat(sut.getVolumes()).isEqualTo(MANGA_ONE.getVolumes());
        assertThat(sut.getStatus()).isEqualTo(MANGA_ONE.getStatus());
        assertThat(sut.getAuthor()).isEqualTo(MANGA_ONE.getAuthor());
        assertThat(sut.getPublisher()).isEqualTo(MANGA_ONE.getPublisher());
        assertThat(sut.getDateReleased()).isEqualTo(MANGA_ONE.getDateReleased());
        assertThat(sut.getDateAdded()).isEqualTo(MANGA_ONE.getDateAdded());
        assertThat(sut.getCovers()).isEqualTo(MANGA_ONE.getCovers());
    }

    @Test
    public void createManga_WithInvalidData_ThrowsException() {
        Manga emptyManga = new Manga();
        Manga invalidManga = INVALID_MANGA;

        assertThatThrownBy(() -> mangaRepository.save(emptyManga)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> mangaRepository.save(invalidManga)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void createManga_WithExistingName_ThrowsException() {
        Manga manga = testEntityManager.merge(MANGA_ONE);
        testEntityManager.detach(manga);
        manga.setId(null);

        assertThatThrownBy(() -> mangaRepository.save(manga)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getManga_ByExistingId_ReturnsManga() {
        Manga manga = testEntityManager.merge(MANGA_ONE);

        Optional<Manga> mangaOpt = mangaRepository.findById(manga.getId());

        assertThat(mangaOpt).isNotEmpty();
        assertThat(mangaOpt.get()).isEqualTo(manga);
    }

    @Test
    public void getManga_ByUnexistingId_ReturnsEmpty() {
        Optional<Manga> mangaOpt = mangaRepository.findById(1L);

        assertThat(mangaOpt).isEmpty();
    }

    @Test
    public void getManga_ByExistingName_ReturnsManga() {
        Manga manga = testEntityManager.merge(MANGA_ONE);

        Optional<Manga> mangaOpt = mangaRepository.findByName(manga.getName());

        assertThat(mangaOpt).isNotEmpty();
        assertThat(mangaOpt.get()).isEqualTo(manga);
    }

    @Test
    public void getManga_ByUnexistingName_ReturnsEmpty() {
        Optional<Manga> mangaOpt = mangaRepository.findByName("name");

        assertThat(mangaOpt).isEmpty();
    }

    @Test
    public void listMangas_ReturnsMangas() {
        testEntityManager.merge(MANGA_ONE);
        testEntityManager.merge(MANGA_TWO);

        List<Manga> mangas = mangaRepository.findAll();

        assertThat(mangas).isNotEmpty();
        assertThat(mangas).hasSize(2);
    }

    @Test
    public void listMagas_ReturnsNoPlanets() {
        List<Manga> mangas = mangaRepository.findAll();

        assertThat(mangas).isEmpty();
    }

    @Test
    public void removeManga_WithExistingId_RemovesMangaFromDatabase() {
        Manga manga = testEntityManager.persistAndFlush(MANGA_ONE);

        mangaRepository.deleteById(manga.getId());

        Manga removedManga = testEntityManager.find(Manga.class, manga.getId());

        assertThat(removedManga).isNull();
    }

    @Test
    public void removeManga_WithUnexistingId_ThrowsException() {
        Long id = 1L;

        Optional<Manga> manga = mangaRepository.findById(id);

        if (manga == null) {
            assertThrows(EmptyResultDataAccessException.class, () -> {
                mangaRepository.deleteById(id);
            });
        }
    }
}
