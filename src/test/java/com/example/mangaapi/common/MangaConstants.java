package com.example.mangaapi.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.entity.Volume;

public class MangaConstants {

        public static final LocalDateTime DATE_ONE = LocalDateTime.of(2023, 7, 1, 12, 0);
        public static final LocalDateTime DATE_TWO = LocalDateTime.of(2023, 8, 15, 15, 30);

        public static final Manga MANGA_ONE = new Manga(
                        "Manga One",
                        "Description One",
                        new ArrayList<>(),
                        "Ongoing",
                        "Author One",
                        "Publisher One",
                        DATE_ONE,
                        DATE_TWO,
                        null);
        public static final Manga MANGA_TWO = new Manga(
                        "Manga Two",
                        "Description Two",
                        new ArrayList<>(),
                        "Ongoing",
                        "Author Two",
                        "Publisher Two",
                        DATE_ONE,
                        DATE_TWO,
                        null);
        public static final Manga UPDATE_MANGA = new Manga(
                        1L,
                        "One Piece",
                        "Adventure",
                        new ArrayList<>(),
                        "ongoing",
                        "Eiichiro Oda",
                        "Shonen Jump",
                        null,
                        null,
                        null);
        public static final List<Manga> VALID_MANGAS = Arrays.asList(MANGA_ONE, MANGA_TWO);

        public static final Manga INVALID_MANGA = new Manga(
                        "",
                        "",
                        null,
                        "",
                        "",
                        "",
                        null,
                        null,
                        null);

        public static final Volume VOLUME_ONE = new Volume(
                        1,
                        null,
                        null,
                        "Volume One Cover");
}
