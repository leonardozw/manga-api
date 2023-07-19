package com.example.mangaapi.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "mangas")
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true)
    private Volume volumes;

    private String status;
    private Date dateReleased;

    @ElementCollection
    @CollectionTable(name = "manga_covers", joinColumns = @JoinColumn(name = "manga_id"))
    @Column(name = "cover")
    private List<String> Covers;

    public Manga() {
    }

    public Manga(String name, String description, Volume volumes, String status, Date dateReleased,
            List<String> covers) {
        this.name = name;
        this.description = description;
        this.volumes = volumes;
        this.status = status;
        this.dateReleased = dateReleased;
        Covers = covers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Volume getVolumes() {
        return volumes;
    }

    public void setVolumes(Volume volumes) {
        this.volumes = volumes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(Date dateReleased) {
        this.dateReleased = dateReleased;
    }

    public List<String> getCovers() {
        return Covers;
    }

    public void setCovers(List<String> covers) {
        Covers = covers;
    }

}
