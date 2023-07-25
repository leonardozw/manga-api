package com.example.mangaapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.mangaapi.entity.Chapter;
import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.entity.Volume;
import com.example.mangaapi.repository.MangaRepository;
import com.example.mangaapi.repository.VolumeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VolumeService {

    private VolumeRepository volumeRepository;

    private MangaRepository mangaRepository;

    public VolumeService(VolumeRepository volumeRepository, MangaRepository mangaRepository) {
        this.volumeRepository = volumeRepository;
        this.mangaRepository = mangaRepository;
    }

    public Volume create(Volume volume) {
        return volumeRepository.save(volume);
    }

    public Volume addChapter(Long id, Chapter chp) {
        Optional<Volume> optVolume = volumeRepository.findById(id);
        if (optVolume.isPresent()) {
            Volume volume = optVolume.get();
            volume.getChapters().add(0, chp);
            chp.setVolume(volume);
            return volumeRepository.save(volume);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public List<Volume> getAll() {
        return volumeRepository.findAll();
    }

    public Optional<Volume> getById(Long id) {
        return volumeRepository.findById(id);
    }

    public Optional<Volume> getByTitle(String title) {
        return volumeRepository.findByTitle(title);
    }

    public List<Volume> listByMangaId(Long id) {
        Sort sort = Sort.by(Sort.Direction.ASC, "issue");
        Optional<Manga> optManga = mangaRepository.findById(id);
        if (!optManga.isPresent()) {
            throw new EntityNotFoundException();
        } else {
            return volumeRepository.findAllByManga(optManga.get(), sort);
        }
    }

    public Volume update(Volume volume) {
        return volumeRepository.save(volume);
    }

    public List<Volume> delete(Long id) {
        volumeRepository.deleteById(id);
        return getAll();
    }
}
