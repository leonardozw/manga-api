package com.example.mangaapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mangaapi.entity.Manga;

public interface MangaRepository extends JpaRepository<Manga, Long> {
    Optional<Manga> findByName(String name);
}
