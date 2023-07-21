package com.example.mangaapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.entity.Volume;
import com.example.mangaapi.service.MangaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/mangas")
public class MangaController {

    private MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @PostMapping
    public ResponseEntity<Manga> create(@RequestBody @Valid Manga manga) {
        Manga mangaCreated = mangaService.create(manga);
        return ResponseEntity.status(HttpStatus.CREATED).body(mangaCreated);
    }

    @PostMapping("/{id}/add")
    public ResponseEntity<Manga> addVolume(@PathVariable("id") Long id, @RequestBody Volume volume) {
        Manga mangaUpdated = mangaService.addVolume(id, volume);
        return ResponseEntity.ok(mangaUpdated);
    }

    @GetMapping
    public ResponseEntity<List<Manga>> list() {
        List<Manga> mangas = mangaService.list();
        return ResponseEntity.ok(mangas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manga> getById(@PathVariable("id") Long id) {
        return mangaService.getById(id).map(manga -> ResponseEntity.ok(manga))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Manga> getByName(@PathVariable("name") String name) {
        return mangaService.getByName(name).map(manga -> ResponseEntity.ok(manga))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Manga> update(@RequestBody Manga manga) {
        Manga updatedManga = mangaService.update(manga);
        return ResponseEntity.ok(updatedManga);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<List<Manga>> delete(@PathVariable("id") Long id) {
        List<Manga> updatedMangaList = mangaService.delete(id);
        return ResponseEntity.ok(updatedMangaList);
    }
}
