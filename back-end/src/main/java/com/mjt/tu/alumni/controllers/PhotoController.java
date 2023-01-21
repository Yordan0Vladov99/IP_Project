package com.mjt.tu.alumni.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mjt.tu.alumni.repos.PhotoRepository;
import com.mjt.tu.alumni.services.PhotoService;

@RestController
@RequestMapping("/photos")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    public PhotoController(PhotoRepository photoRepository, PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/upload")
    private void createPhoto(@RequestParam("file") MultipartFile file, @RequestParam("user") String userId,
            @RequestParam("group") long groupId) {
        this.photoService.uploadPhoto(userId, groupId, file);
    }

    @DeleteMapping("/removePhoto/{id}")
    private String deletePhoto(@PathVariable("id") String id) {
        return photoService.deletePhoto(id);
    }
}
