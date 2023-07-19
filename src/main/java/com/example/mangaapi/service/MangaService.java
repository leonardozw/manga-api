package com.example.mangaapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.repository.MangaRepository;

@Service
public class MangaService {

    private MangaRepository mangaRepository;

    public MangaService(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    public Manga create(Manga manga) {
        return mangaRepository.save(manga);
    }

    public List<Manga> list() {
        Sort sort = Sort.by("dateAdded").descending().and(Sort.by("name").ascending());
        return mangaRepository.findAll(sort);
    }

    public Optional<Manga> getByName(String name) {
        return mangaRepository.findByName(name);
    }

    public Manga update(Manga manga) {
        return mangaRepository.save(manga);
    }

    public List<Manga> delete(Long id) {
        mangaRepository.deleteById(id);
        return list();
    }
}
