package com.example.mangaapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.entity.Volume;

public interface VolumeRepository extends JpaRepository<Volume, Long> {

    List<Volume> findAllByManga(Manga manga, Sort sort);

    Optional<Volume> findByTitle(String title);
}
