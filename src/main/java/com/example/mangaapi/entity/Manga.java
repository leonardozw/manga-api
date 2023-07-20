package com.example.mangaapi.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;

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
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "mangas")
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;
    @NotBlank
    private String description;

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL)
    private List<Volume> volumes;

    @NotBlank
    private String status;
    @NotBlank
    private String author;
    @NotBlank
    private String publisher;

    private Date dateReleased;
    private Date dateAdded;

    @ElementCollection
    @CollectionTable(name = "manga_covers", joinColumns = @JoinColumn(name = "manga_id"))
    @Column(name = "cover")
    private List<String> covers;

    public Manga() {
    }

    public Manga(Long id, @NotBlank String name, @NotBlank String description, List<Volume> volumes,
            @NotBlank String status, @NotBlank String author, @NotBlank String publisher, Date dateReleased,
            Date dateAdded, List<String> covers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.volumes = volumes;
        this.status = status;
        this.author = author;
        this.publisher = publisher;
        this.dateReleased = dateReleased;
        this.dateAdded = dateAdded;
        this.covers = covers;
    }

    public Manga(String name, String description, List<Volume> volumes, String status, String author, String publisher,
            Date dateReleased, Date dateAdded, List<String> covers) {
        this.name = name;
        this.description = description;
        this.volumes = volumes;
        this.status = status;
        this.author = author;
        this.publisher = publisher;
        this.dateReleased = dateReleased;
        this.dateAdded = dateAdded;
        this.covers = covers;
    }

    public Manga(Long id, @NotBlank String name, @NotBlank String description, @NotBlank String status,
            @NotBlank String author, @NotBlank String publisher, Date dateReleased, Date dateAdded) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.author = author;
        this.publisher = publisher;
        this.dateReleased = dateReleased;
        this.dateAdded = dateAdded;
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

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(Date dateReleased) {
        this.dateReleased = dateReleased;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<String> getCovers() {
        return covers;
    }

    public void setCovers(List<String> covers) {
        this.covers = covers;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(obj, this);
    }

}
