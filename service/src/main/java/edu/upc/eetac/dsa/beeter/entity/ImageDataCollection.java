package edu.upc.eetac.dsa.beeter.entity;

import java.util.ArrayList;
import java.util.List;

public class ImageDataCollection
{
    private List<Image> images = new ArrayList<>();

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addImage(Image image) {
        images.add(image);
    }
}
