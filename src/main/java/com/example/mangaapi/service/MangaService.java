package com.example.mangaapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.entity.Volume;
import com.example.mangaapi.repository.MangaRepository;

import jakarta.persistence.EntityNotFoundException;

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

    public Optional<Manga> getById(Long id) {
        return mangaRepository.findById(id);
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

    public Manga addVolume(Long id, Volume volume) {
        Optional<Manga> optManga = mangaRepository.findById(id);
        if (optManga.isPresent()) {
            Manga manga = optManga.get();
            volume.setManga(manga);
            manga.getVolumes().add(0, volume);
            return mangaRepository.save(manga);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
