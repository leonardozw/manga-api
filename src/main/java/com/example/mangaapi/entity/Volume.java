package com.example.mangaapi.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "volumes")
public class Volume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private int issue;
    @NotEmpty
    private String title;

    @ManyToOne
    @JoinColumn(name = "manga_id")
    @JsonIgnore
    private Manga manga;

    @OneToMany(mappedBy = "volume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapter> chapters;
    private String cover;

    public Volume() {
    }

    public Volume(Long id, @NotNull int issue, @NotEmpty String title, Manga manga, List<Chapter> chapters,
            String cover) {
        this.id = id;
        this.issue = issue;
        this.title = title;
        this.manga = manga;
        this.chapters = chapters;
        this.cover = cover;
    }

    public Volume(@NotNull int issue, @NotEmpty String title, Manga manga, List<Chapter> chapters, String cover) {
        this.issue = issue;
        this.title = title;
        this.manga = manga;
        this.chapters = chapters;
        this.cover = cover;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Volume [id=" + id + ", issue=" + issue + ", title=" + title + ", manga=" + manga + ", chapters="
                + chapters + ", cover=" + cover + "]";
    }

}
