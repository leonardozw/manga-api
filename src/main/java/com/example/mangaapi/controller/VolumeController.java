package com.example.mangaapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mangaapi.service.VolumeService;

@RestController
@RequestMapping("/volumes")
public class VolumeController {

    private VolumeService volumeService;
}
