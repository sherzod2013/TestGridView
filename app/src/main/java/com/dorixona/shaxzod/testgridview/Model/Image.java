package com.dorixona.shaxzod.testgridview.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dorixona.shaxzod.testgridview.R;

/**
 * Created by Shaxzod on 08.08.2017.
 */

public class Image {
    private int id;
    private String path;
    private int child_id;
    private String size_type;
    private int log_image;
    private String created_at;
    private String updated_at;
    private Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public String getSize_type() {
        return size_type;
    }

    public void setSize_type(String size_type) {
        this.size_type = size_type;
    }

    public int getLog_image() {
        return log_image;
    }

    public void setLog_image(int log_image) {
        this.log_image = log_image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
