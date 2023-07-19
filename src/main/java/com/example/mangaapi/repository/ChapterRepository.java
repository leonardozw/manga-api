package com.example.mangaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mangaapi.entity.Chapter;

public interface ChapterRepository extends JpaRepository<Long, Chapter> {

}
