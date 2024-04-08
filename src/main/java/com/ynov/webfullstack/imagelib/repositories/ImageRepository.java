package com.ynov.webfullstack.imagelib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ynov.webfullstack.imagelib.models.Image;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByName(String name);

    List<Image> findByFileName(String fileName);

    List<Image> findByGalleriesName(String galleryName);
}
