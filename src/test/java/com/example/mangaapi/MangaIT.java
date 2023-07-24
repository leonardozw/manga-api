package com.example.mangaapi;

import static com.example.mangaapi.common.MangaConstants.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.entity.Volume;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = { "/import_mangas.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = { "/remove_mangas.sql" }, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class MangaIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createManga_WithValidData_ReturnsCreated() {
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
    }

    @Test
    public void createManga_WithInvalidData_ReturnsBadRequest() {
        ResponseEntity<Manga> sut = restTemplate.postForEntity("/mangas", INVALID_MANGA, Manga.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void getManga_ByExistingId_ReturnsManga() {
        ResponseEntity<Manga> sut = restTemplate.getForEntity("/mangas/1", Manga.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isEqualTo(IT_MANGA);
    }

    @Test
    public void getManga_ByUnexistingId_ReturnsNotFound() {
        ResponseEntity<Manga> sut = restTemplate.getForEntity("/mangas/99", Manga.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getManga_ByExistingName_ReturnsManga() {
        ResponseEntity<Manga> sut = restTemplate.getForEntity("/mangas/name/" + IT_MANGA.getName(), Manga.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isEqualTo(IT_MANGA);
    }

    @Test
    public void getManga_ByUnexistingName_ReturnsNotFound() {
        ResponseEntity<Manga> sut = restTemplate.getForEntity("/mangas/name/test", Manga.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void listMangas_ReturnsMangas() {
        ResponseEntity<Manga[]> sut = restTemplate.getForEntity("/mangas", Manga[].class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).hasSize(3);
    }

    @Test
    public void addVolume_WithExistingMangaAndValidVolume_ReturnsManga() {
        ResponseEntity<Manga> sut = restTemplate.postForEntity("/mangas/add/1", VOLUME_ONE, Manga.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody().getVolumes()).isNotNull();
    }

    @Test
    public void addVolume_WithInvalidData_ReturnsBadRequest() {
        Volume volume = new Volume();
        ResponseEntity<Manga> sut = restTemplate.postForEntity("/mangas/add/1", volume, Manga.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void addVolume_WithUnexistingManga_ReturnsNotFound() {
        Volume volume = VOLUME_ONE;
        ResponseEntity<Manga> sut = restTemplate.postForEntity("/mangas/add/99", volume, Manga.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void removeManga_ReturnsMangas() {
        ResponseEntity<List<Manga>> sut = restTemplate.exchange("/mangas/1", HttpMethod.DELETE, null,
                new ParameterizedTypeReference<List<Manga>>() {
                });

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).hasSize(2);
    }

}
