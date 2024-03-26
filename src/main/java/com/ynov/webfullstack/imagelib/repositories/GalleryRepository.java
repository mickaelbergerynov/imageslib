package com.ynov.webfullstack.imagelib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ynov.webfullstack.imagelib.models.Gallery;
import java.util.List;


public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    List<Gallery> findByName(String name);
}
