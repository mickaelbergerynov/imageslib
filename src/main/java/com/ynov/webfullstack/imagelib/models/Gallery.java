package com.ynov.webfullstack.imagelib.models;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Gallery {
    private @Id @GeneratedValue Long id;
    private String name;

    @ManyToMany
    private List<Image> images;

    public Gallery() {
    }

    public Gallery(String name) {
        this.name = name;
    }

    public void addImage(Image image) {
        this.images.add(image);
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Gallery))
            return false;
        Gallery gallery = (Gallery) o;
        return this.id.equals(gallery.id)
                && this.name.equals(gallery.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "Gallery{ id=" + this.id + ", name='" + this.name + ", nbImages=" + this.images.size() + "'}";
    }
}