package com.example.mangaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mangaapi.entity.Volume;

public interface VolumeRepository extends JpaRepository<Volume, Long> {

}
