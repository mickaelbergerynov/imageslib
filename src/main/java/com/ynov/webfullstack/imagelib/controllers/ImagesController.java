package com.ynov.webfullstack.imagelib.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.ynov.webfullstack.imagelib.models.Image;
import com.ynov.webfullstack.imagelib.repositories.ImageRepository;
import com.ynov.webfullstack.imagelib.services.ImageFileService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ImagesController {

    @Autowired
    private ImageRepository imageRepository;

    public ImagesController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping("/images")
    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    @GetMapping("/images/{id}")
    public Image getById(@RequestParam Long id) {
        Image img = imageRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find image with id " + id));

        return img;
    }

    @PostMapping(path = "/images", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Image createImage(@RequestPart String imageName, @RequestPart("file") MultipartFile file) {
        if (!imageRepository.findByName(imageName).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An image with the same name already exists");
        }

        String sentFilename = file.getOriginalFilename();
        String imageFilename = UUID.randomUUID().toString() + sentFilename.substring(sentFilename.lastIndexOf("."));
        Image img = new Image(imageName, imageFilename);

        imageRepository.save(img);
        try {
            ImageFileService.saveImageFile(imageFilename, file.getBytes());
        } catch (IOException exc) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error at creating image file: " + exc.getMessage());
        }

        return img;
    }

    @DeleteMapping("/images/{id}")
    public void deleteImage(@PathVariable Long id) {
        Image img = imageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String fileName = img.getFileName();
        imageRepository.deleteById(id);
        try {
            ImageFileService.removeImageFile(fileName);
        } catch (IOException exc) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error at removing image file: " + exc.getMessage());
        }
    }

    @DeleteMapping("/images")
    public void deleteAll() {
        List<Image> images = imageRepository.findAll();
        images.forEach((img) -> {
            try {
                ImageFileService.removeImageFile(img.getFileName());
            } catch (IOException exc) {
                System.out.println(exc.getMessage());
            }
        });

        imageRepository.deleteAll();
    }
}
