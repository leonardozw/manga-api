package com.example.mangaapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mangaapi.entity.Manga;
import com.example.mangaapi.service.MangaService;

@RestController
@RequestMapping("/mangas")
public class MangaController {

    private MangaService mangaService;

    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @PostMapping
    Manga create(@RequestBody Manga manga) {
        return mangaService.create(manga);
    }

    @GetMapping
    List<Manga> list() {
        return mangaService.list();
    }

    @PutMapping
    Manga update(@RequestBody Manga manga) {
        return mangaService.update(manga);
    }

    @DeleteMapping("{id}")
    List<Manga> delete(@PathVariable("id") Long id) {
        return mangaService.delete(id);
    }
}
