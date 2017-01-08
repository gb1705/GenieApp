package com.neuron.genieapp.DataModels;

import android.graphics.Bitmap;

/**
 * Created by Karan on 6/12/16.
 */

public class ImageItem {
    private Bitmap image;


    public ImageItem(Bitmap image) {
        super();
        this.image = image;

    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}