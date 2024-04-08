package com.ynov.webfullstack.imagelib.models;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Image {
    private @Id @GeneratedValue Long id;
    private String name;
    private String fileName;

    @JsonIgnore
    @ManyToMany(mappedBy = "images")
    private List<Gallery> galleries;

    public Image() {
    }

    public Image(String name) {
        this.name = name;
    }

    public Image(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileUrl) {
        this.fileName = fileUrl;
    }

    public List<Gallery> getGalleries() {
        return galleries;
    }

    public void setGalleries(List<Gallery> galleries) {
        this.galleries = galleries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Image))
            return false;
        Image image = (Image) o;
        return this.id.equals(image.id)
                && this.name.equals(image.name)
                && this.fileName.equals(image.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.fileName);
    }

    @Override
    public String toString() {
        return "Image{ id=" + this.id + ", name='" + this.name + ", fileName=" + this.fileName + ", nbGalleries: "
                + galleries.size() + "'}";
    }
}
