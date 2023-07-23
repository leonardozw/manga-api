package com.example.mangaapi;

import static com.example.mangaapi.common.MangaConstants.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.example.mangaapi.entity.Manga;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = { "/import_mangas.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = { "/remove_mangas.sql" }, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class MangaIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createManga_ReturnsCreated() {
        ResponseEntity<Manga> sut = restTemplate.postForEntity("/mangas", MANGA_ONE, Manga.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(sut.getBody().getId()).isNotNull();
        assertThat(sut.getBody().getName()).isEqualTo(MANGA_ONE.getName());
        assertThat(sut.getBody().getDescription()).isEqualTo(MANGA_ONE.getDescription());
        assertThat(sut.getBody().getVolumes()).isEqualTo(MANGA_ONE.getVolumes());
        assertThat(sut.getBody().getStatus()).isEqualTo(MANGA_ONE.getStatus());
        assertThat(sut.getBody().getAuthor()).isEqualTo(MANGA_ONE.getAuthor());
        assertThat(sut.getBody().getPublisher()).isEqualTo(MANGA_ONE.getPublisher());
        assertThat(sut.getBody().getDateReleased()).isEqualTo(MANGA_ONE.getDateReleased());
        assertThat(sut.getBody().getDateAdded()).isEqualTo(MANGA_ONE.getDateAdded());
        assertThat(sut.getBody().getCovers()).isEqualTo(MANGA_ONE.getCovers());
    }

    @Test
    public void getManga_ReturnsManga() {
        ResponseEntity<Manga> sut = restTemplate.getForEntity("/mangas/1", Manga.class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isEqualTo(IT_MANGA);
    }

}
