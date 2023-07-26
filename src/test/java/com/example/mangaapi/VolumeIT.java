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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.example.mangaapi.entity.Chapter;
import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.entity.Volume;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = { "/import_volumes.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = { "/remove_volumes.sql" }, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class VolumeIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testVolumeImports() {
        ResponseEntity<Volume[]> response = restTemplate.getForEntity("/volumes", Volume[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Volume[] volumes = response.getBody();

        assertThat(volumes).isNotNull();
        assertThat(volumes).hasSize(3);

        assertThat(volumes[0].getId()).isEqualTo(1L);
        assertThat(volumes[0].getIssue()).isEqualTo(1);
        assertThat(volumes[0].getTitle()).isEqualTo("Volume One");
        assertThat(volumes[0].getCover()).isEqualTo("image1.png");

        assertThat(volumes[1].getId()).isEqualTo(2L);
        assertThat(volumes[1].getIssue()).isEqualTo(2);
        assertThat(volumes[1].getTitle()).isEqualTo("Volume Two");
        assertThat(volumes[1].getCover()).isEqualTo("image2.png");

        assertThat(volumes[2].getId()).isEqualTo(3L);
        assertThat(volumes[2].getIssue()).isEqualTo(3);
        assertThat(volumes[2].getTitle()).isEqualTo("Volume Three");
        assertThat(volumes[2].getCover()).isEqualTo("image3.png");
    }

    @Test
    public void createVolume_WithValidData_ReturnsCreated() {
        ResponseEntity<Volume> sut = restTemplate.postForEntity("/volumes", VOLUME_ONE, Volume.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(sut.getBody().getId()).isNotNull();
        assertThat(sut.getBody().getIssue()).isEqualTo(VOLUME_ONE.getIssue());
        assertThat(sut.getBody().getTitle()).isEqualTo(VOLUME_ONE.getTitle());
        assertThat(sut.getBody().getManga()).isEqualTo(VOLUME_ONE.getManga());
        assertThat(sut.getBody().getChapters()).isEqualTo(VOLUME_ONE.getChapters());
        assertThat(sut.getBody().getCover()).isEqualTo(VOLUME_ONE.getCover());
    }

    @Test
    public void createVolume_WithInvalidData_ReturnsUnprocessableEntity() {
        ResponseEntity<Volume> sut = restTemplate.postForEntity("/volumes", INVALID_VOLUME, Volume.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void addChapter_WithExistingVolumeAndValidChapter_ReturnsVolume() {
        ResponseEntity<Volume> sut = restTemplate.postForEntity("/volumes/add/1", CHAPTER_ONE, Volume.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody().getChapters()).isNotNull();
    }

    @Test
    public void addChapter_WithInvalidData_ReturnsUnprocessableEntity() {
        Chapter chapter = new Chapter();
        ResponseEntity<Volume> sut = restTemplate.postForEntity("/volumes/add/1", chapter, Volume.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void addChapter_WithUnexistingVolume_ReturnsNotFound() {
        Chapter chapter = CHAPTER_ONE;
        ResponseEntity<Volume> sut = restTemplate.postForEntity("/volumes/add/99", chapter, Volume.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getAllVolumes_ReturnsAllVolumes() {
        ResponseEntity<Volume[]> sut = restTemplate.getForEntity("/volumes", Volume[].class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).hasSize(3);
    }

    @Test
    public void getVolume_ByExistingId_ReturnsVolume() {
        ResponseEntity<Volume> sut = restTemplate.getForEntity("/volumes/" + IT_VOLUME.getId(), Volume.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody().getId()).isEqualTo(IT_VOLUME.getId());
        assertThat(sut.getBody().getIssue()).isEqualTo(IT_VOLUME.getIssue());
        assertThat(sut.getBody().getTitle()).isEqualTo(IT_VOLUME.getTitle());
        assertThat(sut.getBody().getCover()).isEqualTo(IT_VOLUME.getCover());
    }

    @Test
    public void getVolume_ByUnexistingId_ReturnsNotFound() {
        ResponseEntity<Volume> sut = restTemplate.getForEntity("/volumes/99", Volume.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getVolume_ByExistingTitle_ReturnsManga() {
        ResponseEntity<Volume> sut = restTemplate.getForEntity("/volumes/title/" + IT_VOLUME.getTitle(), Volume.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody().getId()).isEqualTo(IT_VOLUME.getId());
        assertThat(sut.getBody().getIssue()).isEqualTo(IT_VOLUME.getIssue());
        assertThat(sut.getBody().getTitle()).isEqualTo(IT_VOLUME.getTitle());
        assertThat(sut.getBody().getCover()).isEqualTo(IT_VOLUME.getCover());
    }

    @Test
    public void getVolumes_ByExistingManga_ReturnsVolumes() {
        ResponseEntity<Manga> manga = restTemplate.postForEntity("/mangas", MANGA_ONE, Manga.class);
        ResponseEntity<Manga> mangaUpdated = restTemplate.postForEntity("/mangas/add/1", VOLUME_TWO, Manga.class);

        ResponseEntity<List<Volume>> sut = restTemplate.exchange(
                "/volumes/manga/" + mangaUpdated.getBody().getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Volume>>() {
                });

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody().get(0).getId()).isNotNull();
        assertThat(sut.getBody().get(0).getIssue()).isEqualTo(VOLUME_TWO.getIssue());
        assertThat(sut.getBody().get(0).getTitle()).isEqualTo(VOLUME_TWO.getTitle());
        assertThat(sut.getBody().get(0).getManga()).isEqualTo(VOLUME_TWO.getManga());
        assertThat(sut.getBody().get(0).getChapters()).isEqualTo(VOLUME_TWO.getChapters());
        assertThat(sut.getBody().get(0).getCover()).isEqualTo(VOLUME_TWO.getCover());
    }

    @Test
    public void getVolumes_ByUnexistingManga_ReturnsNotFound() {
        ResponseEntity<List<Volume>> sut = restTemplate.exchange(
                "/volumes/manga/" + 99L,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Volume>>() {
                });
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateVolume_WithValidData_ReturnsUpdatedVolume() {
        Volume volume = VOLUME_ONE;
        volume.setId(1L);
        volume.setTitle("Updated Volumes");

        HttpEntity<Volume> requestEntity = new HttpEntity<>(volume);

        ResponseEntity<Volume> sut = restTemplate.exchange("/volumes", HttpMethod.PUT, requestEntity, Volume.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody().getId()).isNotNull();
        assertThat(sut.getBody().getIssue()).isEqualTo(volume.getIssue());
        assertThat(sut.getBody().getTitle()).isEqualTo(volume.getTitle());
        assertThat(sut.getBody().getManga()).isEqualTo(volume.getManga());
        assertThat(sut.getBody().getChapters()).isEqualTo(volume.getChapters());
        assertThat(sut.getBody().getCover()).isEqualTo(volume.getCover());
    }

    @Test
    public void updateVolume_WithInvalidData_ReturnsUnprocessableEntity() {
        Volume volume = VOLUME_ONE;
        volume.setId(1L);
        volume.setTitle("");

        HttpEntity<Volume> requestEntity = new HttpEntity<>(volume);

        ResponseEntity<Volume> sut = restTemplate.exchange("/volumes", HttpMethod.PUT, requestEntity, Volume.class);

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void removeVolume_ByExistingId_ReturnsVolumes() {
        ResponseEntity<List<Volume>> sut = restTemplate.exchange("/volumes/1", HttpMethod.DELETE, null,
                new ParameterizedTypeReference<List<Volume>>() {
                });

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).hasSize(2);
    }

    @Test
    public void removeVolume_ByUnexistingId_ReturnsNotFound() {
        ResponseEntity<List<Volume>> sut = restTemplate.exchange("/volumes/99", HttpMethod.DELETE, null,
                new ParameterizedTypeReference<List<Volume>>() {
                });

        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).hasSize(3);
    }
}
