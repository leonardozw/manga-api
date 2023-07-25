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

import com.example.mangaapi.entity.Chapter;
import com.example.mangaapi.entity.Volume;
import com.example.mangaapi.service.VolumeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/volumes")
public class VolumeController {

    private VolumeService volumeService;

    public VolumeController(VolumeService volumeService) {
        this.volumeService = volumeService;
    }

    @PostMapping
    public ResponseEntity<Volume> create(@RequestBody @Valid Volume volume) {
        Volume volumeCreated = volumeService.create(volume);
        return ResponseEntity.status(HttpStatus.CREATED).body(volumeCreated);
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Volume> addChapter(@PathVariable("id") Long id, @RequestBody @Valid Chapter chapter) {
        Volume volumeUpdated = volumeService.addChapter(id, chapter);
        return ResponseEntity.ok(volumeUpdated);
    }

    @GetMapping()
    public ResponseEntity<List<Volume>> getAll() {
        List<Volume> volumes = volumeService.getAll();
        return ResponseEntity.ok(volumes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volume> getById(@PathVariable("id") Long id) {
        return volumeService.getById(id).map(volume -> ResponseEntity.ok(volume))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Volume> getByTitle(@PathVariable("title") String title) {
        return volumeService.getByTitle(title).map(volume -> ResponseEntity.ok(volume))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/manga/{id}")
    public ResponseEntity<List<Volume>> list(@PathVariable("id") Long id) {
        List<Volume> volumes = volumeService.listByMangaId(id);
        if (volumes.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(volumes);
        }
    }

    @PutMapping
    public ResponseEntity<Volume> update(@RequestBody @Valid Volume volume) {
        Volume volumeUpdated = volumeService.update(volume);
        return ResponseEntity.ok(volumeUpdated);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<List<Volume>> delete(@PathVariable("id") Long id) {
        List<Volume> volumesUpdatedList = volumeService.delete(id);

        if (volumesUpdatedList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(volumesUpdatedList);
        }
    }

}
