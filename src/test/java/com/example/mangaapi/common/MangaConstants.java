package com.example.mangaapi.common;

import java.util.Date;

import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.entity.Volume;

public class MangaConstants {
    public static final Manga MANGA_ONE = new Manga(
            "Manga One",
            "Description One",
            null,
            "Ongoing",
            "Author One",
            "Publisher One",
            new Date(),
            new Date(),
            null);
    public static final Volume VOLUME_ONE = new Volume(
            1,
            null,
            null,
            "Volume One Cover");

}
