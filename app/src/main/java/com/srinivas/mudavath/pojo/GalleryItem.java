package com.srinivas.mudavath.pojo;

import java.io.Serializable;

/**
 * Created by Mudavath Srinivas on 26-08-2015.
 */
public class GalleryItem implements Serializable{

    long id;
    String imagePath;
    String displayName;
    String dateTaken;

    public GalleryItem(long id, String imagePath, String displayName, String dateTaken) {
        this.id = id;
        this.imagePath = imagePath;
        this.displayName = displayName;
        this.dateTaken = dateTaken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }
}
