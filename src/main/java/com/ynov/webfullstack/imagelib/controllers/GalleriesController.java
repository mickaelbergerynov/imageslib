package com.ynov.webfullstack.imagelib.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ynov.webfullstack.imagelib.models.Gallery;
import com.ynov.webfullstack.imagelib.models.Image;
import com.ynov.webfullstack.imagelib.repositories.GalleryRepository;
import com.ynov.webfullstack.imagelib.repositories.ImageRepository;
import com.ynov.webfullstack.imagelib.viewmodels.GalleryViewModel;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin("*")
public class GalleriesController {
    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private ImageRepository imageRepository;

    public GalleriesController(GalleryRepository galleryRepository, ImageRepository imageRepository) {
        this.galleryRepository = galleryRepository;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/galleries")
    public List<Gallery> getAll() {
        return galleryRepository.findAll();
    }

    @PostMapping("/galleries")
    public Gallery add(@RequestBody GalleryViewModel gallery) {
        if (!galleryRepository.findByName(gallery.getName()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A gallery with the same name already exists");
        }

        return galleryRepository.save(new Gallery(gallery.getName()));
    }

    @PutMapping("/galleries/{id}/addImage")
    public Gallery addImageToGallery(@PathVariable Long id, @RequestBody Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cannot find image with the specified id"));

        Gallery gallery = galleryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Gallery with id " + id + " does not exist"));

        gallery.addImage(image);
        return galleryRepository.save(gallery);
    }

    @GetMapping("/galleries/{id}")
    public Gallery getOne(@PathVariable Long id) {
        return galleryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Cannot find gallery with the specified id"));
    }

    @GetMapping("/galleries/{id}/images")
    public List<Image> getImagesForGallery(@PathVariable Long id) {
        return imageRepository.findByGalleriesId(id);
    }

    @DeleteMapping("/galleries/{id}")
    public void removeGallery(@PathVariable Long id) {
        galleryRepository.deleteById(id);
    }

    @DeleteMapping("/galleries")
    public void removeAll() {
        galleryRepository.deleteAll();
    }

}
