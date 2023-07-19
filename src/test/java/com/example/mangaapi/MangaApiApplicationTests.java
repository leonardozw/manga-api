package com.example.mangaapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.mangaapi.entity.Manga;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MangaApiApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void createManga_WithValidArguments_ReturnsManga() {
		var manga = new Manga("jjk", "shounen", null, "ongoing", "gege akutami", "shounem jump", null, null, null);

		webTestClient
				.post()
				.uri("/mangas")
				.bodyValue(manga)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.name").isEqualTo(manga.getName());
	}

	@Test
	void createManga_WithInvalidArguments_ReturnsManga() {
		webTestClient
				.post()
				.uri("/mangas")
				.bodyValue(new Manga("", "", null, "", "", "", null, null, null))
				.exchange()
				.expectStatus().isBadRequest();
	}

}
