package com.example.mangaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mangaapi.entity.Manga;

public interface MangaRepository extends JpaRepository<Long, Manga> {

}
