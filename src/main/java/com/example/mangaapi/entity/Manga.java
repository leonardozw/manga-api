package com.example.mangaapi.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "mangas")
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;
    @NotEmpty
    private String description;

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL)
    private List<Volume> volumes;

    @NotEmpty
    private String status;
    @NotEmpty
    private String author;
    @NotEmpty
    private String publisher;

    private LocalDateTime dateReleased;
    private LocalDateTime dateAdded;

    public Manga() {
    }

    public Manga(Long id, @NotEmpty String name, @NotEmpty String description, List<Volume> volumes,
            @NotEmpty String status, @NotEmpty String author, @NotEmpty String publisher, LocalDateTime dateReleased,
            LocalDateTime dateAdded) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.volumes = volumes;
        this.status = status;
        this.author = author;
        this.publisher = publisher;
        this.dateReleased = dateReleased;
        this.dateAdded = dateAdded;
    }

    public Manga(@NotEmpty String name, @NotEmpty String description, List<Volume> volumes, @NotEmpty String status,
            @NotEmpty String author, @NotEmpty String publisher, LocalDateTime dateReleased, LocalDateTime dateAdded) {
        this.name = name;
        this.description = description;
        this.volumes = volumes;
        this.status = status;
        this.author = author;
        this.publisher = publisher;
        this.dateReleased = dateReleased;
        this.dateAdded = dateAdded;
    }

    public Manga(Manga mangaOne) {
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

    public LocalDateTime getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(LocalDateTime dateReleased) {
        this.dateReleased = dateReleased;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(obj, this);
    }

    @Override
    public String toString() {
        return "Manga [id=" + id + ", name=" + name + ", description=" + description + ", volumes=" + volumes
                + ", status=" + status + ", author=" + author + ", publisher=" + publisher + ", dateReleased="
                + dateReleased + ", dateAdded=" + dateAdded + "]";
    }

}
