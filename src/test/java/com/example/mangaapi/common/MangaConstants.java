package com.example.mangaapi.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.mangaapi.entity.Chapter;
import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.entity.Volume;

public class MangaConstants {

        public static final LocalDateTime DATE_ONE = LocalDateTime.of(2023, 7, 1, 12, 0);
        public static final LocalDateTime DATE_TWO = LocalDateTime.of(2023, 8, 15, 15, 30);

        public static final Chapter CHAPTER_ONE = new Chapter(
                        "test",
                        10,
                        null,
                        null);

        public static final Volume VOLUME_ONE = new Volume(
                        null,
                        1,
                        "Volume One",
                        null,
                        new ArrayList<>(),
                        "Volume One Cover");

        public static final Volume VOLUME_TWO = new Volume(
                        null,
                        1,
                        "Volume Two",
                        null,
                        new ArrayList<>(),
                        "Volume Two Cover");

        public static final Volume IT_VOLUME = new Volume(
                        1L,
                        1,
                        "Volume One",
                        null, null, "image1.png");

        public static final Volume EXPECTED_VOLUME = new Volume(
                        1L,
                        1,
                        "Volume One",
                        null,
                        new ArrayList<>(),
                        "Volume One Cover");

        public static final Manga MANGA_ONE = new Manga(
                        "Manga One",
                        "Description One",
                        new ArrayList<>(),
                        "Ongoing",
                        "Author One",
                        "Publisher One",
                        DATE_ONE,
                        DATE_TWO);
        public static final Manga MANGA_TWO = new Manga(
                        "Manga Two",
                        "Description Two",
                        new ArrayList<>(),
                        "Ongoing",
                        "Author Two",
                        "Publisher Two",
                        DATE_ONE,
                        DATE_TWO);
        public static final Manga UPDATE_MANGA = new Manga(
                        1L,
                        "One Piece",
                        "Adventure",
                        new ArrayList<>(),
                        "ongoing",
                        "Eiichiro Oda",
                        "Shonen Jump",
                        null,
                        null);
        public static final Manga EXPECTED_MANGA = new Manga(
                        1L,
                        "One Piece",
                        "Adventure",
                        Collections.singletonList(VOLUME_ONE),
                        "ongoing",
                        "Eiichiro Oda",
                        "Shonen Jump",
                        null,
                        null);
        public static final Manga IT_MANGA = new Manga(
                        1L,
                        "One Piece",
                        "Aventura e ação",
                        new ArrayList<>(),
                        "Ongoing",
                        "Eiichiro Oda",
                        "Shonen Jump",
                        LocalDateTime.of(2023, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2023, 7, 21, 12, 0, 0));

        public static final List<Manga> VALID_MANGAS = Arrays.asList(MANGA_ONE, MANGA_TWO);
        public static final List<Volume> VALID_VCLUMES = Arrays.asList(VOLUME_ONE, VOLUME_TWO);

        public static final Manga INVALID_MANGA = new Manga(
                        "",
                        "",
                        null,
                        "",
                        "",
                        "",
                        null,
                        null);

        public static final Volume INVALID_VOLUME = new Volume(

        );

        public static final Chapter INVALID_CHAPTER = new Chapter();

}
