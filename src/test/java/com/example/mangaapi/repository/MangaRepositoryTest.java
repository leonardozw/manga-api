package com.example.mangaapi.repository;

import static com.example.mangaapi.common.MangaConstants.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.mangaapi.entity.Manga;

@DataJpaTest
public class MangaRepositoryTest {
    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private TestEntityManager testEntityManager;

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
        Manga manga = testEntityManager.persistAndFlush(MANGA_ONE);
        testEntityManager.detach(manga);
        manga.setId(null);

        assertThatThrownBy(() -> mangaRepository.save(manga)).isInstanceOf(RuntimeException.class);
    }
}
